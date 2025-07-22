package com.gotchai.api.global.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(RsaKeyProperties::class)
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

    @Bean
    fun jwtAuthenticationFilter(rsaKeyProperties: RsaKeyProperties): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(rsaKeyProperties)
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain {
        http.addFilterBefore(jwtAuthenticationFilter, OncePerRequestFilter::class.java)

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