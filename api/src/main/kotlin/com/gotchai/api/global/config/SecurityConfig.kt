package com.gotchai.api.global.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()


    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtConverter: Converter<OAuth2ResourceServerProperties.Jwt, out AbstractAuthenticationToken>,
    ): SecurityFilterChain {

        http
            .cors { }
            .headers { it.frameOptions { option -> option.disable() } }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .exceptionHandling { it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper())) }

        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(
                    "/api/v1/auth/**",
                ).permitAll()

            authorize.anyRequest().authenticated()
        }
        return http.build()
    }
}