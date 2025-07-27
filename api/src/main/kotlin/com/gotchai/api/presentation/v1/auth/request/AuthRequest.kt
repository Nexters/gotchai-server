package com.gotchai.api.presentation.v1.auth.request

class AuthRequest {
    data class AppleLogin(
        val idToken: String,
    )

    data class KakaoLogin(
        val accessToken: String,
    )

    data class Login(
        val email: String,
        val password: String,
    )

    data class Logout(
        val accessToken: String,
    )

    data class RefreshToken(
        val refreshToken: String,
    )

    data class SignUp(
        val name: String,
        val password: String,
        val email: String,
    )
}
