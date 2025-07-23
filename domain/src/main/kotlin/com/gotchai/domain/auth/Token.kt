package com.gotchai.domain.auth

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
