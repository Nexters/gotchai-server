package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.dto.result.GradeQuizResult

data class GradeQuizResponse(
    val contents: String,
    val isAnswer: Boolean,
    val isTimeout: Boolean
) {
    companion object {
        fun from(result: GradeQuizResult): GradeQuizResponse =
            with(result) {
                GradeQuizResponse(
                    contents = contents,
                    isAnswer = isAnswer,
                    isTimeout = isTimeout
                )
            }
    }
}
