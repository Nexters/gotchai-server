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
    val quizzesIds: List<Long>,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(examResult: GetExamResult): ExamDetailResponse =
            ExamDetailResponse(
                id = examResult.id,
                title = examResult.title,
                subTitle = examResult.subTitle,
                descriptionImage = examResult.descriptionImage,
                iconImage = examResult.iconImage,
                theme = examResult.theme,
                quizzesIds = examResult.quizzesIds,
                createdAt = examResult.createdAt
            )
    }
}
