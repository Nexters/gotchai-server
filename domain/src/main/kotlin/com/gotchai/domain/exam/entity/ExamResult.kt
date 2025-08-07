package com.gotchai.domain.exam.entity

import java.time.LocalDateTime

data class ExamResult(
    val id: Long,
    val examId: Long,
    val userId: Long,
    val takeQuizIds: List<Long>,
    val answerQuizIds: List<Long> = emptyList(),
    val failedQuizIds: List<Long> = emptyList(),
    val createdAt: LocalDateTime
) {
    data class Creation(
        val examId: Long,
        val userId: Long,
        val takeQuizIds: String,
        val answerQuizIds: String?,
        val failedQuizIds: String?
    )
}
