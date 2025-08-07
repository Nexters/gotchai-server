package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.entity.QuizPick

data class GradeQuizResponse(
    val contents: String,
    val isAnswer: Boolean
) {
    companion object {
        fun from(quizPick: QuizPick): GradeQuizResponse =
            with(quizPick) {
                GradeQuizResponse(
                    contents = contents,
                    isAnswer = isAnswer
                )
            }
    }
}
