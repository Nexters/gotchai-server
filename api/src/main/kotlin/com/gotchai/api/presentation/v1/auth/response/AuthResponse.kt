package com.gotchai.api.presentation.v1.auth.response

import com.gotchai.domain.auth.dto.TokenPair

class AuthResponse {
    data class Message(
        val message: String,
    )

    data class Token(
        val accessToken: String,
        val refreshToken: String,
    ) {
        companion object {
            fun of(tokenPair: TokenPair) = Token(tokenPair.accessToken, tokenPair.refreshToken)
        }
    }
}
