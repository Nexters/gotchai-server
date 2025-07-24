package com.gotchai.infrastructure.oauth.config

import com.gotchai.infrastructure.oauth.apple.AppleProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.gotchai.infrastructure.oauth"])
@EnableConfigurationProperties(AppleProperties::class)
internal class OAuthFeignConfig
