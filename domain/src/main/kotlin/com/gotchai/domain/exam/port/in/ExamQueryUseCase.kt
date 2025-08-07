package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamById(id: Long): GetExamResult

    fun getExams(): List<Exam>

    fun getExamsByUserId(userId: Long): List<Exam>

    fun getExamParticipantCountById(id: Long): Int
}
