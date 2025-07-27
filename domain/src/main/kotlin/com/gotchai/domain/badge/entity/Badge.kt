package com.gotchai.domain.badge.entity

import java.time.LocalDateTime

data class Badge(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val tier: Tier,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val examId: Long,
        val name: String,
        val description: String,
        val image: String,
        val tier: Tier,
    )
}
