package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.dto.result.GetMyExamResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamById(examId: Long): Exam

    fun getExams(userId: Long): List<GetExamResult>

    fun getMyExams(userId: Long): List<GetMyExamResult>

    fun getExamParticipantCountById(examId: Long): Int
}
