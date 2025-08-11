package com.gotchai.infrastructure.aws.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws")
data class AwsProperties(
    val accessKey: String,
    val secretAccessKey: String,
    val region: String
)
