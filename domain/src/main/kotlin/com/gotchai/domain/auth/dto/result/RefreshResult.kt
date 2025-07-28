package com.gotchai.domain.auth.dto.result

data class RefreshResult(
    val accessToken: String,
    val refreshToken: String,
)
