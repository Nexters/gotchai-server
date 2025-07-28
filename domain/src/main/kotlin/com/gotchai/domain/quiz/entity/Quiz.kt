package com.gotchai.domain.quiz.entity

import java.time.LocalDateTime

data class Quiz(
    val id: Long,
    val examId: Long,
    val contents: String,
    val order: Int,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val examId: Long,
        val contents: String,
        val order: Int,
    )
}
