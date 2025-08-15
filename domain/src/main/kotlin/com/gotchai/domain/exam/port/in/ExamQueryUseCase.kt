package com.gotchai.domain.exam.port.`in`

import com.gotchai.domain.exam.dto.result.GetExamsResult
import com.gotchai.domain.exam.dto.result.GetMyExamsResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryUseCase {
    fun getExamById(examId: Long): Exam

    fun getExams(userId: Long): List<GetExamsResult>

    fun getMyExams(userId: Long): List<GetMyExamsResult>

    fun getExamParticipantCountById(examId: Long): Int
}
