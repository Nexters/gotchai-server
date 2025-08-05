package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamId(examId: Long): Exam

    fun getExamDetailById(examId: Long): GetExamResult

    fun getExamsByUserId(userId: Long): List<Exam>

    fun getExams(): List<Exam>

    fun getExamParticipantCountById(id: Long): Int
}
