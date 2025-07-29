package com.gotchai.infrastructure.oauth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.apple")
data class AppleProperties(
    val bundleId: String
)
