package com.gotchai.domain.quiz.entity

import java.time.LocalDateTime

data class QuizHistory(
    val id: Long,
    val examHistoryId: Long,
    val quizId: Long,
    val quizPickId: Long,
    val isAnswer: Boolean,
    val createdAt: LocalDateTime
) {
    data class Creation(
        val examHistoryId: Long,
        val quizId: Long,
        val quizPickId: Long,
        val isAnswer: Boolean
    )
}
