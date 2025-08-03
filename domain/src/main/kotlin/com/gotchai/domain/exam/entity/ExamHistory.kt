package com.gotchai.domain.exam.entity

import com.gotchai.domain.quiz.entity.QuizHistory
import java.time.Duration
import java.time.LocalDateTime

data class ExamHistory(
    val historyId: String,
    val histories: List<QuizHistory>,
    val examId: Long,
    val createdAt: LocalDateTime,
    val expiration: Duration
) {
    data class Creation(
        val historyId: String,
        val histories: List<QuizHistory>,
        val examId: Long,
        val createdAt: LocalDateTime,
        val expiration: Duration
    )
}
