package com.gotchai.api.presentation.v1.auth.response

import com.gotchai.domain.auth.dto.result.RefreshResult

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(result: RefreshResult): RefreshResponse =
            with(result) {
                RefreshResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
    }
}
