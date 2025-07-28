package com.gotchai.api.global.config

import com.gotchai.api.global.jwt.DefaultJwtProvider
import com.gotchai.api.global.jwt.JwtAuthenticationFilter
import com.gotchai.api.global.jwt.JwtProperties
import com.gotchai.domain.global.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {
    @Bean
    fun jwtProvider(jwtProperties: JwtProperties): JwtProvider = DefaultJwtProvider(jwtProperties)

    @Bean
    fun jwtAuthenticationFilter(jwtProvider: JwtProvider): JwtAuthenticationFilter =
        JwtAuthenticationFilter(jwtProvider)
}
