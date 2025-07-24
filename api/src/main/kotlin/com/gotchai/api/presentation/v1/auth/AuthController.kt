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
import com.gotchai.common.enum.user.SocialType
import com.gotchai.domain.auth.AuthenticationFacade
import com.gotchai.domain.auth.AuthenticationService
import com.gotchai.domain.user.User
import com.gotchai.infrastructure.oauth.OAuthService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthController(
    private val authenticationService: AuthenticationService,
    private val authenticationFacade: AuthenticationFacade,
    private val oauthService: OAuthService,
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
        @RequestHeader(value = "X-DEVICE-ID") deviceId: String?,
        @RequestBody request: AppleLoginRequest,
    ): TokenResponse {
        val appleUserInfo = oauthService.getAppleUserInfo(request.idToken)

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
        val kakaoUserInfo = oauthService.getKaKaoUserInfo(request.accessToken)

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
