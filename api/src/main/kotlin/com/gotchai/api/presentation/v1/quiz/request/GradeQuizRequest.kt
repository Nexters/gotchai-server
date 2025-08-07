package com.gotchai.api.presentation.v1.quiz.request

import com.gotchai.domain.quiz.dto.command.GradeQuizCommand

data class GradeQuizRequest(
    val examId: Long,
    val quizPickId: Long
) {
    fun toCommand(): GradeQuizCommand =
        GradeQuizCommand(
            examId = examId,
            quizPickId = quizPickId
        )
}
