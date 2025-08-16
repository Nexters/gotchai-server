package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.storage.rdb.exam.repository.ExamHistoryJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional

@Adapter
class ExamHistoryQueryAdapter(
    private val examHistoryJpaRepository: ExamHistoryJpaRepository
) : ExamHistoryQueryPort {
    @ReadOnlyTransactional
    override fun getExamHistories(): List<ExamHistory> = examHistoryJpaRepository.findAll().map { it.toExamHistory() }

    @ReadOnlyTransactional
    override fun getAllExamHistoriesWithQuizIds(): List<ExamHistory> =
        examHistoryJpaRepository.findAllWithQuizIds().map { it.toExamHistory() }

    @ReadOnlyTransactional
    override fun getExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistory? =
        examHistoryJpaRepository
            .findByExamIdAndUserId(examId, userId)
            ?.toExamHistory()

    @ReadOnlyTransactional
    override fun getExamHistoriesByExamIdAndIsSolved(
        examId: Long,
        isSolved: Boolean
    ): List<ExamHistory> =
        examHistoryJpaRepository
            .findAllByExamIdAndIsSolved(examId, isSolved)
            .map { it.toExamHistory() }
}
