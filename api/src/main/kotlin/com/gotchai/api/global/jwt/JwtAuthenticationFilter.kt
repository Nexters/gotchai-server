package com.gotchai.api.global.jwt

import com.gotchai.api.global.exception.JwtException
import com.gotchai.domain.global.jwt.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        request.getHeader(HttpHeaders.AUTHORIZATION)
            .runCatching { jwtProvider.getAuthentication(toBearerToken()) }
            .onSuccess { SecurityContextHolder.getContext().authentication = it }
            .run { filterChain.doFilter(request, response) }
    }

    private fun String.toBearerToken(): String =
        if (startsWith("Bearer ")) substring(7) else throw JwtException("Authorization header is invalid.")
}
