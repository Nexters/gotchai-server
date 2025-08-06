package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.QuizPick

interface QuizPickQueryPort {
    fun getQuizPickById(id: Long): QuizPick?

    fun getQuizPicksByQuizId(quizId: Long): List<QuizPick>
}
