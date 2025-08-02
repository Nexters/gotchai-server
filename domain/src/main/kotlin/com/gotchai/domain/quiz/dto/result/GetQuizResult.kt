package com.gotchai.domain.quiz.dto.result

import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.domain.quiz.entity.QuizPick
import java.time.LocalDateTime

data class GetQuizResult(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
    val quizPicks: List<QuizPick>
) {
    companion object {
        fun of(
            quiz: Quiz,
            picks: List<QuizPick>
        ) = GetQuizResult(
            id = quiz.id,
            contents = quiz.contents,
            createdAt = quiz.createdAt,
            quizPicks = picks
        )
    }
}
