package com.gotchai.domain.auth.dto

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)
