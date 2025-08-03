package com.gotchai.domain.exam.port.`in`

interface ExamCommandUseCase {
    fun submit(
        userId: Long,
        examId: Long
    )
}
