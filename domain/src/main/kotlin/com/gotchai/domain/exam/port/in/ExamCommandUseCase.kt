package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.StartExamResult
import com.gotchai.domain.exam.dto.result.SubmitExamResult

interface ExamCommandUseCase {
    fun startExam(
        userId: Long,
        examId: Long
    ): StartExamResult

    fun submitExam(
        userId: Long,
        examId: Long
    ): SubmitExamResult
}
