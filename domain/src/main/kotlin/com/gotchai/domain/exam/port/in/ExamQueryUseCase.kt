package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.ExamResult

interface ExamQueryUseCase {
    fun getExamById(
        userId: Long,
        examId: Long
    ): ExamResult

    fun getExams(userId: Long): List<ExamResult>

    fun getExamsByUserId(userId: Long): List<ExamResult>

    fun getExamParticipantCountById(examId: Long): Int
}
