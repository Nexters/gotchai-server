package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.QuizPick

data class QuizPickResponse(
    val quizId: Long,
    val contents: String,
    val type: AnswerType
) {
    companion object {
        fun from(quizPick: QuizPick): QuizPickResponse =
            with(quizPick) {
                QuizPickResponse(
                    quizId = quizId,
                    contents = contents,
                    type = type
                )
            }
    }
}
