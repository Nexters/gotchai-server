package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.QuizHistory

interface QuizHistoryCommandPort {
    fun createQuizHistory(creation: QuizHistory.Creation): QuizHistory
}
