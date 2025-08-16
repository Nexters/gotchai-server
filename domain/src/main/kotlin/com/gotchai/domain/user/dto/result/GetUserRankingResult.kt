package com.gotchai.domain.user.dto.result

import com.gotchai.domain.user.entity.Profile

data class GetUserRankingResult(
    val name: String,
    val rating: Int
) {
    companion object {
        fun of(
            profile: Profile,
            rating: Int
        ) = GetUserRankingResult(
            name = profile.nickname,
            rating = rating
        )
    }
}
