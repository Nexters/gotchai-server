package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.domain.exam.dto.result.SubmitExamResult

data class SubmitExamResponse(
    val answerCount: Int,
    val badge: BadgeResponse
) {
    companion object {
        fun from(submitExam: SubmitExamResult): SubmitExamResponse =
            SubmitExamResponse(
                answerCount = submitExam.correctAnswerCount,
                badge = BadgeResponse.from(submitExam.badge)
            )
    }
}
