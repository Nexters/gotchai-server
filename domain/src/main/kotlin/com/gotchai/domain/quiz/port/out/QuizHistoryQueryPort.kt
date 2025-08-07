package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.QuizHistory

interface QuizHistoryQueryPort {
    fun getQuizHistoriesByExamHistoryId(examHistoryId: Long): List<QuizHistory>
}
