package com.gotchai.infrastructure.aws.s3.config

import com.gotchai.infrastructure.aws.global.config.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3Config(
    private val awsProperties: AwsProperties
) {
    @Bean
    fun s3Client(credentialsProvider: AwsCredentialsProvider): S3Client =
        S3Client
            .builder()
            .credentialsProvider(credentialsProvider)
            .region(Region.of(awsProperties.region))
            .build()
}
