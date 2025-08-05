package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.ExamSubmitResult

interface ExamCommandUseCase {
    fun submit(
        userId: Long,
        examId: Long
    ): ExamSubmitResult
}
