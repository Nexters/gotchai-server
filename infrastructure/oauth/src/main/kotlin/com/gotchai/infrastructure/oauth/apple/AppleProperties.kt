package com.gotchai.infrastructure.oauth.apple

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("apple")
data class AppleProperties(
    val bundleId: String,
)
