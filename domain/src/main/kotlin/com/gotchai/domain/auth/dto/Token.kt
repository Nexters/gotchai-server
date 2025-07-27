package com.gotchai.domain.auth.dto

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
