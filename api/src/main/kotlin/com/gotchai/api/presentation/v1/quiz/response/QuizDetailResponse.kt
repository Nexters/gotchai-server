package com.gotchai.api.presentation.v1.quiz.response

import com.gotchai.domain.quiz.dto.result.GetQuizResult
import java.time.LocalDateTime

data class QuizDetailResponse(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val quizPicks: List<QuizPickResponse>
) {
    companion object {
        fun from(result: GetQuizResult): QuizDetailResponse =
            with(result) {
                QuizDetailResponse(
                    id = id,
                    contents = contents,
                    createdAt = createdAt,
                    quizPicks = quizPicks.map { QuizPickResponse.from(it) }
                )
            }
    }
}
