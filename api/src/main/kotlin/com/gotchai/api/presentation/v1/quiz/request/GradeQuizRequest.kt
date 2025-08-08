package com.gotchai.api.presentation.v1.quiz.request

import com.gotchai.domain.quiz.dto.command.GradeQuizCommand

data class GradeQuizRequest(
    val quizPickId: Long,
    val isTimeout: Boolean
) {
    fun toCommand(): GradeQuizCommand = GradeQuizCommand(quizPickId = quizPickId, isTimeout = isTimeout)
}
