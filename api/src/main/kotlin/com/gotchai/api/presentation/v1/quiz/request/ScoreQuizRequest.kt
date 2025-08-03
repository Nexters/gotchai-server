package com.gotchai.api.presentation.v1.quiz.request

data class ScoreQuizRequest(
    val examId: Long,
    val quizPickId: Long
)
