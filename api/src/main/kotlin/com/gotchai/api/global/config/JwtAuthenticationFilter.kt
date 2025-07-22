package com.gotchai.api.global.config

import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val rsaKeyProperties: RsaKeyProperties
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        
        val token = authHeader.substring(7)
        
        try {
            val claims = Jwts.parser()
                .verifyWith(rsaKeyProperties.publicKey)
                .build()
                .parseSignedClaims(token)
                .payload
            
            val username = claims.subject
            val authorities = listOf(SimpleGrantedAuthority("USER"))
            
            val userDetails = User(username, "", authorities)
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            // 토큰 검증 실패 시 로깅만 하고 인증 정보는 설정하지 않음
            logger.error("JWT token validation failed: ${e.message}")
        }
        
        filterChain.doFilter(request, response)
    }
}