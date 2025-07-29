package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamById(examId: Long): Exam

    fun getExams(): List<Exam>
}
