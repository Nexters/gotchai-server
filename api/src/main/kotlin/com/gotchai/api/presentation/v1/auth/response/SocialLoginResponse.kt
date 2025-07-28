package com.gotchai.api.presentation.v1.auth.response

import com.gotchai.domain.auth.dto.result.SocialLoginResult

data class SocialLoginResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(result: SocialLoginResult): SocialLoginResponse =
            with(result) {
                SocialLoginResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
    }
}
