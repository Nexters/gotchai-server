package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.TestLoginRequest
import com.gotchai.api.presentation.v1.auth.request.TestSignUpRequest
import com.gotchai.api.presentation.v1.auth.response.TestLoginResponse
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Profile("local | dev")
@ApiV1Controller
class AuthTestController(
    private val authCommandUseCase: AuthCommandUseCase
) {
    @PostMapping("/auth/test/login")
    fun testLogin(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: TestLoginRequest
    ): TestLoginResponse =
        authCommandUseCase
            .login(deviceId, request.toCommand())
            .let { TestLoginResponse.from(it) }

    @PostMapping("/auth/test/sign-up")
    fun testSignUp(
        @RequestBody
        request: TestSignUpRequest
    ) {
        authCommandUseCase.signUp(request.toCommand())
    }
}
