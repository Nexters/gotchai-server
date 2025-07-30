package com.gotchai.api.util

import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import org.springframework.test.web.reactive.server.expectBody

fun ResponseSpec.expectError(): BodySpec<ApiResponse<ErrorResponse>, *> = expectBody<ApiResponse<ErrorResponse>>()
