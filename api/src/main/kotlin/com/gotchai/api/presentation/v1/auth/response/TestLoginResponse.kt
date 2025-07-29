package com.gotchai.api.presentation.v1.auth.response

import com.gotchai.domain.auth.dto.result.LoginResult

data class TestLoginResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(result: LoginResult): TestLoginResponse =
            with(result) {
                TestLoginResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
    }
}
