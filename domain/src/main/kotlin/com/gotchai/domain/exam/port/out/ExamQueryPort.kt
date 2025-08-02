package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.Exam

interface ExamQueryPort {
    fun getExamById(examId: Long): Exam

    fun getExams(): List<Exam>

    fun getExamsByInIn(ids: Collection<Long>): List<Exam>
}
