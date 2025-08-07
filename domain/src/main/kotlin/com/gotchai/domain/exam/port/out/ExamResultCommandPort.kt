package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamResult

interface ExamResultCommandPort {
    fun createExamResult(creation: ExamResult.Creation)
}
