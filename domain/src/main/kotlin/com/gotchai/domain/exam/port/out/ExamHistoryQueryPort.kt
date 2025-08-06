package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory

interface ExamHistoryQueryPort {
    fun getHistoryById(
        userId: Long,
        examId: Long
    ): ExamHistory?
}
