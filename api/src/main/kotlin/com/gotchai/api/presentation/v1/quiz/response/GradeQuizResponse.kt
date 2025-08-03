package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.dto.result.QuizPickResult

data class GradeQuizResponse(
    val contents: String,
    val isAnswer: Boolean
) {
    companion object {
        fun from(result: QuizPickResult): GradeQuizResponse =
            with(result) {
                GradeQuizResponse(
                    contents = contents,
                    isAnswer = isAnswer
                )
            }
    }
}
