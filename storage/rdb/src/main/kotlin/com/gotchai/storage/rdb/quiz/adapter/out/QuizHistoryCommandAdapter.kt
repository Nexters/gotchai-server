package com.gotchai.storage.rdb.quiz.adapter.out

import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.port.out.QuizHistoryCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.quiz.entity.QuizHistoryEntity
import com.gotchai.storage.rdb.quiz.repository.QuizHistoryJpaRepository

@Adapter
class QuizHistoryCommandAdapter(
    private val quizHistoryJpaRepository: QuizHistoryJpaRepository
) : QuizHistoryCommandPort {
    override fun createQuizHistory(creation: QuizHistory.Creation): QuizHistory =
        quizHistoryJpaRepository
            .save(QuizHistoryEntity.from(creation))
            .toQuizHistory()

    override fun deleteQuizHistoriesByExamHistoryId(examHistoryId: Long) {
        quizHistoryJpaRepository.deleteAllByExamHistoryId(examHistoryId)
    }
}
