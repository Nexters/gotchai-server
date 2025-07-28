package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshRequest
import com.gotchai.api.presentation.v1.auth.response.RefreshResponse
import com.gotchai.api.presentation.v1.auth.response.SocialLoginResponse
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import com.gotchai.domain.global.security.GotchaiAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthController(
    private val authCommandUseCase: AuthCommandUseCase,
) {
    @PostMapping("/auth/login/apple")
    fun appleLogin(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: AppleLoginRequest,
    ): SocialLoginResponse =
        authCommandUseCase.socialLogin(deviceId, request.toCommand())
            .let { SocialLoginResponse.from(it) }

    @PostMapping("/auth/login/kakao")
    fun kakaoLogin(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: KakaoLoginRequest,
    ): SocialLoginResponse =
        authCommandUseCase.socialLogin(deviceId, request.toCommand())
            .let { SocialLoginResponse.from(it) }

    @PostMapping("/auth/logout")
    fun logout(
        @AuthenticationPrincipal
        authentication: GotchaiAuthentication,
    ) {
        authCommandUseCase.logout(authentication.userId)
    }

    @PostMapping("/auth/refresh")
    fun refresh(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: RefreshRequest,
    ): RefreshResponse =
        authCommandUseCase.refresh(deviceId, request.toCommand())
            .let { RefreshResponse.from(it) }
}
