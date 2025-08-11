package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.Exam

interface ExamCommandPort {
    fun createExam(creation: Exam.Creation): Exam
}
