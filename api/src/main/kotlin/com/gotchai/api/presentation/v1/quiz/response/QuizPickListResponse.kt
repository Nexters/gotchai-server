package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.entity.QuizPick

data class QuizPickListResponse(
    val list: List<QuizPickResponse>
) {
    companion object {
        fun from(quizPicks: List<QuizPick>): QuizPickListResponse = QuizPickListResponse(quizPicks.map { QuizPickResponse.from(it) })
    }
}
