package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory

interface ExamHistoryQueryPort {
    fun getHistoryById(historyId: String): ExamHistory?
}
