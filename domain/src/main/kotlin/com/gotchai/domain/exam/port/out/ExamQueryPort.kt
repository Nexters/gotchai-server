package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.dto.projection.ExamWithExamHistory
import com.gotchai.domain.exam.dto.projection.SolvedExamWithExamHistory
import com.gotchai.domain.exam.entity.Exam

interface ExamQueryPort {
    fun getExamById(id: Long): Exam?

    fun getExams(): List<Exam>

    fun getExamsWithExamHistoryByUserIdAndIsSolved(
        userId: Long,
        isSolved: Boolean?
    ): List<ExamWithExamHistory>

    fun getSolvedExamsWithExamHistoryByUserIdAndIsSolved(
        userId: Long,
        isSolved: Boolean?
    ): List<SolvedExamWithExamHistory>

    fun getExamCount(): Long
}
