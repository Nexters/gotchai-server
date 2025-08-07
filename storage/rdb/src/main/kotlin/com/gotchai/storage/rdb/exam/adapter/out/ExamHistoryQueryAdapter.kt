package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.storage.rdb.exam.repository.ExamHistoryJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class ExamHistoryQueryAdapter(
    private val examHistoryJpaRepository: ExamHistoryJpaRepository
) : ExamHistoryQueryPort {
    override fun getExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistory? =
        examHistoryJpaRepository
            .findByExamIdAndUserId(examId, userId)
            ?.toExamHistory()

    override fun getExamHistoriesByExamIdAndSolvedTrue(examId: Long): List<ExamHistory> =
        examHistoryJpaRepository
            .findAllByExamIdAndSolvedTrue(examId)
            .map { it.toExamHistory() }

    override fun getExamHistoriesByUserIdAndSolvedTrue(userId: Long): List<ExamHistory> =
        examHistoryJpaRepository
            .findAllByUserIdAndSolvedTrue(userId)
            .map { it.toExamHistory() }
}
