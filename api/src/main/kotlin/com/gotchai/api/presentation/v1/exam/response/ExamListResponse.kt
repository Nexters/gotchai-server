package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.entity.Exam

data class ExamListResponse(
    val list: List<ExamResponse>
) {
    companion object {
        fun from(exams: List<Exam>): ExamListResponse = ExamListResponse(exams.map { ExamResponse.from(it) })
    }
}
