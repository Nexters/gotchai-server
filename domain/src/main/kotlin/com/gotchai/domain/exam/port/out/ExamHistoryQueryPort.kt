package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory

interface ExamHistoryQueryPort {
    fun getExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistory?

    fun getExamHistoriesByExamIdAndSolvedTrue(examId: Long): List<ExamHistory>

    fun getExamHistoriesByUserIdAndSolvedTrue(userId: Long): List<ExamHistory>
}
