package com.gotchai.infrastructure.aws.cloudfront.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws.cloudfront")
data class CloudFrontProperties(
    val domain: String
)
