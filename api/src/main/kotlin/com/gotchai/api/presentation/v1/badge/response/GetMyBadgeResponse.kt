package com.gotchai.api.presentation.v1.badge.response

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAt
import java.time.LocalDateTime

data class GetMyBadgeResponse(
    val id: Long,
    val name: String,
    val image: String,
    val acquiredAt: LocalDateTime
) {
    companion object {
        fun from(badge: BadgeWithAcquiredAt): GetMyBadgeResponse =
            with(badge) {
                GetMyBadgeResponse(
                    id = id,
                    name = name,
                    image = image,
                    acquiredAt = acquiredAt
                )
            }
    }
}
