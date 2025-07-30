package com.gotchai.api.presentation.v1.badge.response

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import java.time.LocalDateTime

data class BadgeResponse(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(badge: Badge): BadgeResponse =
            with(badge) {
                BadgeResponse(
                    id = id,
                    examId = examId,
                    name = name,
                    description = description,
                    image = image,
                    tier = tier,
                    createdAt = createdAt
                )
            }
    }
}
