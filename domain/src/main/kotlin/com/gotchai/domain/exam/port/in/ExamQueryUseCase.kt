package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamById(examId: Long): GetExamResult

    fun getExams(): List<Exam>
}
