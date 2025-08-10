package com.gotchai.ai.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
data class GeminiProperties(
    val apiKey: String,
    val model: String
)
