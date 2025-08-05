package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.domain.exam.dto.result.ExamSubmitResult

data class SubmitExamResponse(
    val title: String,
    val subTitle: String,
    val theme: String,
    val iconImage: String,
    val prompt: String,
    val answerCount: Long,
    val badge: BadgeResponse
) {
    companion object {
        fun from(submitExam: ExamSubmitResult): SubmitExamResponse =
            SubmitExamResponse(
                title = submitExam.exam.title,
                subTitle = submitExam.exam.subTitle,
                theme = submitExam.exam.theme,
                iconImage = submitExam.exam.iconImage,
                prompt = submitExam.exam.prompt,
                answerCount = submitExam.correctAnswerCount.toLong(),
                badge = BadgeResponse.from(submitExam.badge)
            )
    }
}
