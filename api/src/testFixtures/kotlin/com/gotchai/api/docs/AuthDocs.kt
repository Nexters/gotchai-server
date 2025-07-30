package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshRequest
import com.gotchai.api.presentation.v1.auth.response.RefreshResponse
import com.gotchai.api.presentation.v1.auth.response.SocialLoginResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf

val appleLoginRequestFields =
    fieldsOf(
        AppleLoginRequest::idToken bodyDesc "ID 토큰"
    )

val kakaoLoginRequestFields =
    fieldsOf(
        KakaoLoginRequest::accessToken bodyDesc "카카오 액세스 토큰"
    )

val refreshRequestFields =
    fieldsOf(
        RefreshRequest::refreshToken bodyDesc "리프레시 토큰"
    )

val refreshResponseFields =
    fieldsOf(
        RefreshResponse::accessToken bodyDesc "액세스 토큰",
        RefreshResponse::refreshToken bodyDesc "리프레시 토큰"
    )

val socialLoginResponseFields =
    fieldsOf(
        SocialLoginResponse::accessToken bodyDesc "액세스 토큰",
        SocialLoginResponse::refreshToken bodyDesc "리프레시 토큰"
    )
