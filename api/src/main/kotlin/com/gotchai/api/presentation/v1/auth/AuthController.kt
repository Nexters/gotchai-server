package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.auth.request.AuthRequest
import com.gotchai.api.presentation.v1.auth.response.AuthResponse
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
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
    private val oAuthQueryUseCase: OAuthQueryUseCase,
) {
    @PostMapping("/auth/test-login")
    fun testLogin(
        @RequestBody request: AuthRequest.Login,
    ): AuthResponse.Token =
        AuthResponse.Token.of(
            authenticationCommandUseCase.testLogin(
                request.email,
                request.password,
            ),
        )

    @PostMapping("/auth/test-signup")
    fun testSignUp(
        @RequestBody request: AuthRequest.SignUp,
    ): AuthResponse.Message {
        authenticationCommandUseCase.testSignUp(
            name = request.name,
            email = request.email,
            password = request.password,
        )
        return AuthResponse.Message("회원가입이 완료되었습니다.")
    }

    @PostMapping("/auth/login/apple")
    fun appleLogin(
        @RequestHeader(value = "X-DEVICE-ID") deviceId: String?,
        @RequestBody request: AuthRequest.AppleLogin,
    ): AuthResponse.Token {
        val appleUserInfo = oAuthQueryUseCase.getAppleUserInfo(request.idToken)

        return AuthResponse.Token.of(
            authenticationCommandUseCase.socialLogin(
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
        @RequestBody request: AuthRequest.KakaoLogin,
    ): AuthResponse.Token {
        val kakaoUserInfo = oAuthQueryUseCase.getKaKaoUserInfo(request.accessToken)

        return AuthResponse.Token.of(
            authenticationCommandUseCase.socialLogin(
                deviceId = deviceId,
                email = kakaoUserInfo.email,
                socialId = kakaoUserInfo.id,
                socialType = SocialType.KAKAO,
            ),
        )
    }

    @PostMapping("/auth/logout")
    fun logout(
        @RequestBody request: AuthRequest.Logout,
    ): AuthResponse.Message {
        val logout = authenticationCommandUseCase.logout(request.accessToken)
        return AuthResponse.Message("로그아웃 되었습니다. userId=$logout")
    }

    @DeleteMapping("/auth/withdrawal")
    fun withdrawal(user: User.Issue): AuthResponse.Message {
        authenticationCommandUseCase.withdrawUser(user.id)
        return AuthResponse.Message("회원탈퇴가 완료되었습니다.")
    }

    @PostMapping("/auth/refresh")
    fun refresh(
        @RequestBody request: AuthRequest.RefreshToken,
    ): AuthResponse.Token {
        val token = authenticationCommandUseCase.renew(request.refreshToken)
        return AuthResponse.Token.of(token)
    }
}
