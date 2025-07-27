package com.gotchai.domain.auth.dto

data class TokenWithAuthentication(
    val accessToken: String,
    val refreshToken: String,
    val deviceId: String?,
    val provider: ProviderDetail,
)
