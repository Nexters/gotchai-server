package com.gotchai.api.presentation.v1.auth.response

import com.gotchai.domain.auth.Token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun of(token: Token) = TokenResponse(token.accessToken, token.refreshToken)
    }
}
