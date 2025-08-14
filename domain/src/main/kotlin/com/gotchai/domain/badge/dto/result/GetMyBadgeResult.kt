package com.gotchai.domain.badge.dto.result

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAtProjection
import com.gotchai.domain.badge.entity.Tier
import java.time.LocalDateTime

data class GetMyBadgeResult(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val createdAt: LocalDateTime,
    val acquiredAt: LocalDateTime
) {
    companion object {
        fun from(projection: BadgeWithAcquiredAtProjection): GetMyBadgeResult =
            with(projection) {
                GetMyBadgeResult(
                    id = id,
                    examId = examId,
                    name = name,
                    description = description,
                    image = image,
                    tier = tier,
                    createdAt = createdAt,
                    acquiredAt = acquiredAt
                )
            }
    }
}
