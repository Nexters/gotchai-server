package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.entity.QuizPick

data class QuizPickResponse(
    val id: Long,
    val contents: String
) {
    companion object {
        fun from(quizPick: QuizPick): QuizPickResponse =
            with(quizPick) {
                QuizPickResponse(
                    id = id,
                    contents = contents
                )
            }
    }
}
