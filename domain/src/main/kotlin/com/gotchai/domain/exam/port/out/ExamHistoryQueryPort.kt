package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory

interface ExamHistoryQueryPort {
    fun getAllExamHistoriesWithQuizIds(): List<ExamHistory>

    fun getExamHistoryByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistory?

    fun getExamHistoriesByExamIdAndIsSolved(
        examId: Long,
        isSolved: Boolean
    ): List<ExamHistory>

    fun getExamHistories(): List<ExamHistory>
}
