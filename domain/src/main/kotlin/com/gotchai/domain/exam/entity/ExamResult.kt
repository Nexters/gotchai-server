package com.gotchai.domain.exam.entity

import java.time.LocalDateTime

data class ExamResult(
    val id: Long,
    val examId: Long,
    val userId: Long,
    val answerCount: Int,
    val createdAt: LocalDateTime
) {
    data class Creation(
        val examId: Long,
        val userId: Long,
        val answerCount: Int
    )
}
