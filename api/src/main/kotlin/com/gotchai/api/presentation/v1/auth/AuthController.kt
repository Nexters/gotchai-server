package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.LoginRequest
import com.gotchai.api.presentation.v1.auth.request.LogoutRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshTokenRequest
import com.gotchai.api.presentation.v1.auth.request.SignUpRequest
import com.gotchai.api.presentation.v1.auth.response.LogoutResponse
import com.gotchai.api.presentation.v1.auth.response.SignUpResponse
import com.gotchai.api.presentation.v1.auth.response.TokenResponse
import com.gotchai.api.presentation.v1.auth.response.WithdrawalResponse
import com.gotchai.domain.auth.adapter.`in`.AuthenticationFacade
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
import com.gotchai.domain.auth.port.`in`.AuthenticationQueryUseCase
import com.gotchai.domain.user.entity.SocialType
import com.gotchai.domain.user.entity.User
import com.gotchai.infrastructure.oauth.port.`in`.OAuthQueryUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthController(
    private val authenticationCommandUseCase: AuthenticationCommandUseCase,
    private val authenticationQueryUseCase: AuthenticationQueryUseCase,
    private val authenticationFacade: AuthenticationFacade,
    private val oAuthQueryUseCase: OAuthQueryUseCase,
) {
    @PostMapping("/auth/test-login")
    fun testLogin(
        @RequestBody request: LoginRequest,
    ): TokenResponse =
        TokenResponse.of(
            authenticationCommandUseCase.testLogin(
                request.email,
                request.password,
            ),
        )

    @PostMapping("/auth/test-signup")
    fun testSignUp(
        @RequestBody request: SignUpRequest,
    ): SignUpResponse {
        authenticationCommandUseCase.testSignUp(
            name = request.name,
            email = request.email,
            password = request.password,
        )
        return SignUpResponse("회원가입이 완료되었습니다.")
    }

    @PostMapping("/auth/login/apple")
    fun appleLogin(
        @RequestHeader(value = "X-DEVICE-ID") deviceId: String?,
        @RequestBody request: AppleLoginRequest,
    ): TokenResponse {
        val appleUserInfo = oAuthQueryUseCase.getAppleUserInfo(request.idToken)

        return TokenResponse.of(
            authenticationFacade.socialLogin(
                deviceId = deviceId,
                email = appleUserInfo.email,
                socialId = appleUserInfo.id,
                socialType = SocialType.APPLE,
            ),
        )
    }

    @PostMapping("/auth/login/kakao")
    fun kakaoLogin(
        @RequestHeader(value = "X-DEVICE-ID") deviceId: String?,
        @RequestBody request: KakaoLoginRequest,
    ): TokenResponse {
        val kakaoUserInfo = oAuthQueryUseCase.getKaKaoUserInfo(request.accessToken)

        return TokenResponse.of(
            authenticationFacade.socialLogin(
                deviceId = deviceId,
                email = kakaoUserInfo.email,
                socialId = kakaoUserInfo.id,
                socialType = SocialType.KAKAO,
            ),
        )
    }

    @PostMapping("/auth/logout")
    fun logout(
        @RequestBody request: LogoutRequest,
    ): LogoutResponse {
        val logout = authenticationCommandUseCase.logout(request.accessToken)
        return LogoutResponse("로그아웃 되었습니다. userId=$logout")
    }

    @DeleteMapping("/auth/withdrawal")
    fun withdrawal(user: User.Issue): WithdrawalResponse {
        authenticationCommandUseCase.withdrawUser(user.id)
        return WithdrawalResponse("회원탈퇴가 완료되었습니다.")
    }

    @PostMapping("/auth/refresh")
    fun refresh(
        @RequestBody request: RefreshTokenRequest,
    ): TokenResponse {
        val token = authenticationCommandUseCase.renew(request.refreshToken)
        return TokenResponse.of(token)
    }
}
