package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.storage.rdb.exam.entity.ExamHistoryEntity
import com.gotchai.storage.rdb.exam.repository.ExamHistoryJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow

@Adapter
class ExamHistoryCommandAdapter(
    private val examHistoryJpaRepository: ExamHistoryJpaRepository
) : ExamHistoryCommandPort {
    override fun createExamHistory(creation: ExamHistory.Creation): ExamHistory =
        examHistoryJpaRepository
            .save(ExamHistoryEntity.from(creation))
            .toExamHistory()

    override fun updateExamHistory(examHistory: ExamHistory): ExamHistory =
        with(examHistory) {
            val examHistoryEntity = examHistoryJpaRepository.findByIdOrElseThrow(id)

            examHistoryEntity.also {
                it.quizIds = quizIds
                it.correctAnswerCount = correctAnswerCount
                it.isSolved = isSolved
            }

            examHistoryJpaRepository
                .save(examHistoryEntity)
                .toExamHistory()
        }

    override fun deleteExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ) {
        examHistoryJpaRepository.deleteByExamIdAndUserId(examId, userId)
    }
}
