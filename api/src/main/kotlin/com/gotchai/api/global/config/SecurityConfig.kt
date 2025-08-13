package com.gotchai.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.api.global.jwt.JwtAuthenticationFilter
import com.gotchai.api.global.security.CustomAccessDeniedHandler
import com.gotchai.api.global.security.CustomAuthenticationEntryPoint
import com.gotchai.domain.global.provider.TokenProvider
import com.gotchai.domain.user.entity.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
        customAccessDeniedHandler: CustomAccessDeniedHandler,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain =
        with(http) {
            csrf { it.disable() }
            formLogin { it.disable() }
            logout { it.disable() }
            sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            exceptionHandling {
                it
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            }
            authorizeHttpRequests {
                it
                    .requestMatchers("/ping")
                    .permitAll()
                    .requestMatchers(
                        "/api/v1/auth/login/**",
                        "/api/v1/auth/refresh",
                        "/api/v1/auth/test/**"
                    ).permitAll()
                    .requestMatchers("/api/v1/admin/**")
                    .hasAuthority(Role.ADMIN.name)
                    .anyRequest()
                    .authenticated()
            }
            addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            build()
        }

    @Bean
    fun customAuthenticationEntryPoint(objectMapper: ObjectMapper): CustomAuthenticationEntryPoint =
        CustomAuthenticationEntryPoint(objectMapper)

    @Bean
    fun customAccessDeniedHandler(objectMapper: ObjectMapper): CustomAccessDeniedHandler = CustomAccessDeniedHandler(objectMapper)

    @Bean
    fun jwtAuthenticationFilter(tokenProvider: TokenProvider): JwtAuthenticationFilter = JwtAuthenticationFilter(tokenProvider)

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}
