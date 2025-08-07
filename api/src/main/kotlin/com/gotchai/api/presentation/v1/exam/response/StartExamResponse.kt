package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.StartExamResult

data class StartExamResponse(
    val quizIds: List<Long>
) {
    companion object {
        fun from(result: StartExamResult): StartExamResponse = StartExamResponse(quizIds = result.quizIds)
    }
}
