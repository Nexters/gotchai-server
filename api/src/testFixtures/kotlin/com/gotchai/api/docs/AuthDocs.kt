package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshRequest
import com.gotchai.api.presentation.v1.auth.response.RefreshResponse
import com.gotchai.api.presentation.v1.auth.response.SocialLoginResponse
import com.gotchai.api.util.bodyDesc

val appleLoginRequestFields =
    listOf(
        AppleLoginRequest::idToken bodyDesc "ID 토큰"
    )

val kakaoLoginRequestFields =
    listOf(
        KakaoLoginRequest::accessToken bodyDesc "카카오 액세스 토큰"
    )

val refreshRequestFields =
    listOf(
        RefreshRequest::refreshToken bodyDesc "리프레시 토큰"
    )

val refreshResponseFields =
    listOf(
        RefreshResponse::accessToken bodyDesc "액세스 토큰",
        RefreshResponse::refreshToken bodyDesc "리프레시 토큰"
    )

val socialLoginResponseFields =
    listOf(
        SocialLoginResponse::accessToken bodyDesc "액세스 토큰",
        SocialLoginResponse::refreshToken bodyDesc "리프레시 토큰"
    )
