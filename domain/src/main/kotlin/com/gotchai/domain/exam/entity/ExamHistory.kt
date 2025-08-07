package com.gotchai.domain.exam.entity

import java.time.LocalDateTime

data class ExamHistory(
    val id: Long,
    val examId: Long,
    val userId: Long,
    val quizIds: List<Long>,
    val correctAnswerCount: Int,
    val isSolved: Boolean,
    val createdAt: LocalDateTime
) {
    data class Creation(
        val examId: Long,
        val userId: Long,
        val quizIds: List<Long>,
        val correctAnswerCount: Int = 0,
        val isSolved: Boolean = false
    )
}
