package com.gotchai.domain.badge.dto.projection

import com.gotchai.domain.badge.entity.Tier
import java.time.LocalDateTime

data class BadgeWithAcquiredAt(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val createdAt: LocalDateTime,
    val acquiredAt: LocalDateTime
)
