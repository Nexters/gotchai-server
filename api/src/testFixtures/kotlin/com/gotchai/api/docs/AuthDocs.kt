package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.auth.request.AuthRequest
import com.gotchai.api.presentation.v1.auth.response.AuthResponse
import com.gotchai.api.util.bodyDesc

val authTokenResponseFields =
    listOf(
        AuthResponse.Token::accessToken bodyDesc "액세스 토큰",
        AuthResponse.Token::refreshToken bodyDesc "리프레시 토큰",
    )

val authMessageResponseFields =
    listOf(
        AuthResponse.Message::message bodyDesc "응답 메시지",
    )

val testLoginRequestFields =
    listOf(
        AuthRequest.Login::email bodyDesc "이메일",
        AuthRequest.Login::password bodyDesc "비밀번호",
    )

val testSignUpRequestFields =
    listOf(
        AuthRequest.SignUp::name bodyDesc "사용자 이름",
        AuthRequest.SignUp::email bodyDesc "이메일",
        AuthRequest.SignUp::password bodyDesc "비밀번호",
    )

val appleLoginRequestFields =
    listOf(
        AuthRequest.AppleLogin::idToken bodyDesc "Apple ID 토큰",
    )

val kakaoLoginRequestFields =
    listOf(
        AuthRequest.KakaoLogin::accessToken bodyDesc "카카오 액세스 토큰",
    )

val logoutRequestFields =
    listOf(
        AuthRequest.Logout::accessToken bodyDesc "로그아웃할 액세스 토큰",
    )

val refreshTokenRequestFields =
    listOf(
        AuthRequest.RefreshToken::refreshToken bodyDesc "리프레시 토큰",
    )
