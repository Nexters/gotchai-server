package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamType

interface ExamQueryPort {
    fun getExamById(id: Long): Exam?

    fun getExamByType(type: ExamType): Exam?

    fun getExamsByTypeNot(type: ExamType): List<Exam>

    fun getExamsByInIn(ids: Collection<Long>): List<Exam>
}
