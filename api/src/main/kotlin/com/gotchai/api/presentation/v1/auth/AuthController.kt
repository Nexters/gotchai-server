package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshRequest
import com.gotchai.api.presentation.v1.auth.response.RefreshResponse
import com.gotchai.api.presentation.v1.auth.response.SocialLoginResponse
import com.gotchai.api.presentation.v1.auth.response.WithdrawalResponse
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthController(
    private val authCommandUseCase: AuthCommandUseCase
) {
    @PostMapping("/auth/login/apple")
    fun appleLogin(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: AppleLoginRequest
    ): SocialLoginResponse =
        authCommandUseCase
            .socialLogin(deviceId, request.toCommand())
            .let { SocialLoginResponse.from(it) }

    @PostMapping("/auth/login/kakao")
    fun kakaoLogin(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: KakaoLoginRequest
    ): SocialLoginResponse =
        authCommandUseCase
            .socialLogin(deviceId, request.toCommand())
            .let { SocialLoginResponse.from(it) }

    @PostMapping("/auth/logout")
    fun logout(
        @AuthenticationPrincipal
        userId: Long
    ) {
        authCommandUseCase.logout(userId)
    }

    @PostMapping("/auth/refresh")
    fun refresh(
        @RequestHeader(value = "X-DEVICE-ID")
        deviceId: String?,
        @RequestBody
        request: RefreshRequest
    ): RefreshResponse =
        authCommandUseCase
            .refresh(deviceId, request.toCommand())
            .let { RefreshResponse.from(it) }

    @DeleteMapping("/auth/withdrawal")
    fun withdrawal(
        @AuthenticationPrincipal
        userId: Long
    ): WithdrawalResponse =
        authCommandUseCase
            .withdrawal(userId)
            .let { WithdrawalResponse("회원탈퇴가 완료되었습니다.") }
}
