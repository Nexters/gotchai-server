package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory

interface ExamHistoryCommandPort {
    fun createExamHistory(creation: ExamHistory.Creation): ExamHistory

    fun updateExamHistory(examHistory: ExamHistory): ExamHistory
}
