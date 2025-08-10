package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.ExamResult

data class ExamListResponse(
    val list: List<ExamResponse>
) {
    companion object {
        fun from(exams: List<ExamResult>): ExamListResponse = ExamListResponse(exams.map { ExamResponse.from(it) })
    }
}
