package com.gotchai.domain.quiz.entity

import java.time.Duration
import java.time.LocalDateTime

data class QuizScore(
    val quizScoreId: String,
    val scores: List<QuizPickScore>,
    val startQuizId: Long? = null,
    val createdAt: LocalDateTime,
    val expiration: Duration
) {
    data class Creation(
        val quizScoreId: String,
        val scores: List<QuizPickScore>,
        val startQuizId: Long? = null,
        val createdAt: LocalDateTime,
        val expiration: Duration
    )
}
