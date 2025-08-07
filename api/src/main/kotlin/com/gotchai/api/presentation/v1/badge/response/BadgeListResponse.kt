package com.gotchai.api.presentation.v1.badge.response

import com.gotchai.domain.badge.entity.Badge

data class BadgeListResponse(
    val list: List<BadgeResponse>
) {
    companion object {
        fun from(results: List<Badge>): BadgeListResponse = BadgeListResponse(list = results.map { BadgeResponse.from(it) })
    }
}
