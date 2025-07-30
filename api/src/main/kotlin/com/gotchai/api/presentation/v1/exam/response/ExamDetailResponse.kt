package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetExamResult
import java.time.LocalDateTime

data class ExamDetailResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val descriptionImage: String,
    val iconImage: String,
    val theme: String,
    val quizIds: List<Long>,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(result: GetExamResult): ExamDetailResponse =
            with(result) {
                ExamDetailResponse(
                    id = id,
                    title = title,
                    subTitle = subTitle,
                    descriptionImage = descriptionImage,
                    iconImage = iconImage,
                    theme = theme,
                    quizIds = quizIds,
                    createdAt = createdAt
                )
            }
    }
}
