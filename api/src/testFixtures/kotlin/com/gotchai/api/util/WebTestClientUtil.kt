package com.gotchai.api.util

import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import org.springframework.core.io.ByteArrayResource
import org.springframework.test.web.reactive.server.WebTestClient.*
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.BodyInserters
import kotlin.reflect.full.memberProperties

fun ResponseSpec.expectError(): BodySpec<ApiResponse<ErrorResponse>, *> = expectBody<ApiResponse<ErrorResponse>>()

inline fun <reified T : Any> RequestBodySpec.bodyForm(body: T): RequestHeadersSpec<*> =
    LinkedMultiValueMap<String, Any>()
        .apply {
            T::class.memberProperties.forEach {
                val key = it.name
                val value = it.getter.call(body)

                if (value != null) {
                    when (value) {
                        is MultipartFile -> {
                            val resource =
                                object : ByteArrayResource(value.bytes) {
                                    override fun getFilename() = value.originalFilename
                                }

                            add(key, resource)
                        }
                        else -> add(key, value)
                    }
                }
            }
        }.let { body(BodyInserters.fromMultipartData(it)) }
