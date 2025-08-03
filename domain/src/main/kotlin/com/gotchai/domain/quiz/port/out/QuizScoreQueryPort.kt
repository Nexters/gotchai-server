package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.QuizScore

interface QuizScoreQueryPort {
    fun getScoreById(quizScoreId: String): QuizScore?
}
