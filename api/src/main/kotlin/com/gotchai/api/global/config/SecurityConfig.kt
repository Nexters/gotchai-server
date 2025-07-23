package com.gotchai.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(RsaKeyProperties::class)
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtConverter: Converter<Jwt, out AbstractAuthenticationToken>,
        objectMapper: ObjectMapper,
    ): SecurityFilterChain {
        http.oauth2ResourceServer {
            it.jwt { jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwtConverter)
            }
        }

        http
            .cors { }
            .headers { it.frameOptions { option -> option.disable() } }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling { it.authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper)) }

        http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(
                    "/h2-console/**",
                    "/actuator/**",
                    "/ping",
                    "/api.yml",
                ).permitAll()

            authorize
                .requestMatchers(
                    "/api/v1/auth/**",
                    "/api/v1/user/**",
                ).permitAll()

            authorize.anyRequest().authenticated()
        }
        return http.build()
    }
}
