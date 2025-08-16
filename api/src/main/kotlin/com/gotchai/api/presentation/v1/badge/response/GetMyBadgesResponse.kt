package com.gotchai.api.presentation.v1.badge.response

import com.gotchai.domain.badge.dto.result.GetMyBadgesResult

data class GetMyBadgesResponse(
    val badges: List<GetMyBadgeResponse>,
    val totalBadgeCount: Long
) {
    companion object {
        fun from(result: GetMyBadgesResult): GetMyBadgesResponse =
            with(result) {
                GetMyBadgesResponse(
                    badges = badges.map { GetMyBadgeResponse.from(it) },
                    totalBadgeCount = totalBadgeCount
                )
            }
    }
}
