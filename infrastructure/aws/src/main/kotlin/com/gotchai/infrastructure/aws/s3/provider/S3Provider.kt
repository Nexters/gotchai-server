package com.gotchai.infrastructure.aws.s3.provider

import com.gotchai.domain.global.provider.ObjectStorageProvider
import com.gotchai.infrastructure.aws.cloudfront.config.CloudFrontProperties
import com.gotchai.infrastructure.aws.s3.config.S3Properties
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class S3Provider(
    private val s3Client: S3Client,
    private val s3Properties: S3Properties,
    private val cloudFrontProperties: CloudFrontProperties,
    @Value("\${SPRING_PROFILES_ACTIVE}")
    private val env: String
) : ObjectStorageProvider {
    override fun uploadFile(key: String, file: ByteArray): String =
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(s3Properties.bucket)
                .key("$env/$key")
                .build(),
            RequestBody.fromBytes(file)
        ).run { "https://${cloudFrontProperties.domain}/$env/$key" }
}
