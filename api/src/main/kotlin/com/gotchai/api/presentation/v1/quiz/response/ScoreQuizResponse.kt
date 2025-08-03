package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.dto.result.QuizPickResult

data class ScoreQuizResponse(
    val contents: String,
    val isAnswer: Boolean
) {
    companion object {
        fun from(result: QuizPickResult): ScoreQuizResponse {
            with(result) {
                ScoreQuizResponse(
                    contents = contents,
                    isAnswer = isAnswer
                )
            }
        }
    }
}
