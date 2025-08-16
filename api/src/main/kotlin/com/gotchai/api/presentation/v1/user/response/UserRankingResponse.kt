package com.gotchai.api.presentation.v1.user.response

import com.gotchai.domain.user.dto.result.GetUserRankingResult

data class UserRankingResponse(
    val name: String,
    val rating: Double
) {
    companion object {
        fun from(result: GetUserRankingResult) =
            with(result) {
                UserRankingResponse(
                    name = name,
                    rating = rating
                )
            }
    }
}
