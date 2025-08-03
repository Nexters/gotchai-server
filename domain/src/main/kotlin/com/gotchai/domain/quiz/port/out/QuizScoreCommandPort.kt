package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.QuizPickScore

interface QuizScoreCommandPort {
    fun create(
        quizScoreId: String,
        scores: List<QuizPickScore>,
        examId: Long
    )

    fun updateScore(
        quizScoreId: String,
        scores: List<QuizPickScore>
    )
}
