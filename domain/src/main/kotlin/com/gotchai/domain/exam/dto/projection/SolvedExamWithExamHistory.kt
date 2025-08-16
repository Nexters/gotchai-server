package com.gotchai.domain.exam.dto.projection

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamHistory

data class SolvedExamWithExamHistory(
    val exam: Exam,
    val examHistory: ExamHistory
)
