package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.dto.result.ExamResult
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryPort {
    fun getExamById(id: Long): Exam?

    fun getExams(): List<Exam>

    fun getExamResultsByUserIdAndExamId(
        userId: Long,
        examId: Long
    ): ExamResult?

    fun getExamResultsByUserId(userId: Long): List<ExamResult>

    fun getExamsByInIn(ids: Collection<Long>): List<Exam>

    fun getExamResultsByUserIdWithSolvedStatus(
        userId: Long,
        isSolved: Boolean
    ): List<ExamResult>
}
