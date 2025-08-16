package com.gotchai.api.global.jwt

import com.gotchai.domain.global.provider.TokenProvider
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            ?.run {
                runCatching { tokenProvider.getAuthentication(getBearerToken()) }
                    .onSuccess { SecurityContextHolder.getContext().authentication = it }
                    .onFailure { if (it !is JwtException) throw it }
            }

        filterChain.doFilter(request, response)
    }

    private fun String.getBearerToken(): String =
        if (startsWith("Bearer ")) substring(7) else throw JwtException("Authorization header is invalid.")
}
