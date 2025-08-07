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
    override fun getExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistory? =
        examHistoryJpaRepository
            .findByExamIdAndUserId(examId, userId)
            ?.toExamHistory()

    @ReadOnlyTransactional
    override fun getExamHistoriesByExamIdAndSolvedTrue(examId: Long): List<ExamHistory> =
        examHistoryJpaRepository
            .findAllByExamIdAndIsSolvedTrue(examId)
            .map { it.toExamHistory() }

    @ReadOnlyTransactional
    override fun getExamHistoriesByUserIdAndSolvedTrue(userId: Long): List<ExamHistory> =
        examHistoryJpaRepository
            .findAllByUserIdAndIsSolvedTrue(userId)
            .map { it.toExamHistory() }
}
