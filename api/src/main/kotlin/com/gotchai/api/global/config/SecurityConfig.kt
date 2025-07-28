package com.gotchai.api.global.config

import com.gotchai.api.global.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
    ): SecurityFilterChain =
        with(http) {
            csrf { it.disable() }
            formLogin { it.disable() }
            logout { it.disable() }
            sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/ping")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/login/**", "/api/v1/auth/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            build()
        }
}
