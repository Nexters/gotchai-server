package com.gotchai.domain.auth.dto.result

data class SocialLoginResult(
    val accessToken: String,
    val refreshToken: String,
)
