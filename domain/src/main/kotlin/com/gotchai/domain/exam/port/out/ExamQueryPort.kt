package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.dto.projection.ExamWithIsSolved
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryPort {
    fun getExamById(id: Long): Exam?

    fun getExams(): List<Exam>

    fun getExamResultsByUserIdAndExamId(
        userId: Long,
        examId: Long
    ): ExamWithIsSolved?

    fun getExamResultsByUserId(userId: Long): List<ExamWithIsSolved>

    fun getExamsByInIn(ids: Collection<Long>): List<Exam>

    fun getExamResultsByUserIdWithSolvedStatus(
        userId: Long,
        isSolved: Boolean
    ): List<ExamWithIsSolved>

    fun getExamCount(): Long
}
