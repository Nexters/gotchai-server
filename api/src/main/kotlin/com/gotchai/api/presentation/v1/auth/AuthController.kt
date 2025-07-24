package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.LoginRequest
import com.gotchai.api.presentation.v1.auth.request.LogoutRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshTokenRequest
import com.gotchai.api.presentation.v1.auth.request.SignUpRequest
import com.gotchai.api.presentation.v1.auth.response.SignUpResponse
import com.gotchai.api.presentation.v1.auth.response.TokenResponse
import com.gotchai.domain.auth.AuthenticationService
import com.gotchai.domain.user.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class AuthController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/auth/test-login")
    fun testLogin(
        @RequestBody request: LoginRequest,
    ): TokenResponse =
        TokenResponse.of(
            authenticationService.testLogin(
                request.email,
                request.password,
            ),
        )

    @PostMapping("/auth/test-signup")
    fun testSignUp(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse {
        authenticationService.testSignUp(
            name = request.name,
            email = request.email,
            password = request.password,
        )
        return SignUpResponse("회원가입이 완료되었습니다.")
    }

    @PostMapping("/auth/login/apple")
    fun appleLogin(
        @RequestBody request: AppleLoginRequest,
    ) {
    }

    @PostMapping("/auth/login/kakao")
    fun kakaoLogin(
        @RequestBody request: KakaoLoginRequest,
    ) {
    }

    @PostMapping("/auth/logout")
    fun logout(
        @RequestBody request: LogoutRequest,
    ) {
    }

    @PostMapping("/auth/refresh")
    fun refresh(
        @RequestBody request: RefreshTokenRequest,
    ) {
    }

    @DeleteMapping("/auth/withdrawal")
    fun withdrawal(user: User.Issue) {
    }
}
