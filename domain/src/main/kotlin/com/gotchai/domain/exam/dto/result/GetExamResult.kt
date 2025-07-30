package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.exam.entity.Exam
import java.time.LocalDateTime

data class GetExamResult(
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
        fun of(
            exam: Exam,
            quizIds: List<Long>
        ): GetExamResult =
            GetExamResult(
                id = exam.id,
                title = exam.title,
                subTitle = exam.subTitle,
                descriptionImage = exam.descriptionImage,
                iconImage = exam.iconImage,
                theme = exam.theme,
                quizIds = quizIds,
                createdAt = exam.createdAt
            )
    }
}
