package com.gotchai.infrastructure.aws.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider

@Configuration
class AwsConfig(
    private val awsProperties: AwsProperties
) {
    @Bean
    fun credentialsProvider(): AwsCredentialsProvider =
        with(awsProperties) {
            AwsCredentialsProvider { AwsBasicCredentials.create(accessKey, secretAccessKey) }
        }
}
