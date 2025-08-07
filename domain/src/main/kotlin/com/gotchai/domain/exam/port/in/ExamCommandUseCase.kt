package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.SubmitExamResult

interface ExamCommandUseCase {
    fun submitExam(
        userId: Long,
        id: Long
    ): SubmitExamResult
}
