package com.gotchai.storage.rdb.quiz.adapter.out

import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.port.out.QuizHistoryQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.quiz.repository.QuizHistoryJpaRepository

@Adapter
class QuizHistoryQueryAdapter(
    private val quizHistoryJpaRepository: QuizHistoryJpaRepository
) : QuizHistoryQueryPort {
    override fun getQuizHistoriesByExamHistoryId(examHistoryId: Long): List<QuizHistory> =
        quizHistoryJpaRepository
            .findAllByExamHistoryId(examHistoryId)
            .map { it.toQuizHistory() }
}
