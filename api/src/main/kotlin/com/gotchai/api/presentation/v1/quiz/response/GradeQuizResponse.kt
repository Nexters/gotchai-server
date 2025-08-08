package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.entity.QuizPick

data class GradeQuizResponse(
    val contents: String,
    val isAnswer: Boolean,
    val isTimeout: Boolean
) {
    companion object {
        fun of(
            quizPick: QuizPick,
            isTimeout: Boolean
        ): GradeQuizResponse =
            with(quizPick) {
                GradeQuizResponse(
                    contents = contents,
                    isAnswer = isAnswer,
                    isTimeout = isTimeout
                )
            }
    }
}
