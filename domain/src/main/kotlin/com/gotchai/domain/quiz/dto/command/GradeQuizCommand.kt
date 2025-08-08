package com.gotchai.domain.quiz.dto.command

data class GradeQuizCommand(
    val quizPickId: Long,
    val isTimeout: Boolean
)
