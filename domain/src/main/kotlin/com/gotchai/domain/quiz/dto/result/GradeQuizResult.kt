package com.gotchai.domain.quiz.dto.result

data class GradeQuizResult(
    val contents: String,
    val isAnswer: Boolean,
    val isTimeout: Boolean
)
