package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamResult

interface ExamResultQueryPort {
    fun getExamResultsByUserId(userId: Long): List<ExamResult>

    fun countExamResultsByExamId(examId: Long): Int
}
