package com.gotchai.domain.badge.entity

import java.time.LocalDateTime

class Badge {
    data class Creation(
        val examId: Long,
        val name: String,
        val description: String,
        val image: String,
        val tier: Tier,
    )

    data class Info(
        val id: Long,
        val examId: Long,
        val name: String,
        val description: String,
        val image: String,
        val tier: Tier,
        val createdAt: LocalDateTime,
    )
}
