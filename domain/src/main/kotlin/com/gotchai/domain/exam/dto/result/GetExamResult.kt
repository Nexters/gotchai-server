package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.exam.entity.Exam
import java.time.LocalDateTime

data class GetExamResult(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val backgroundImage: String,
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
            with(exam) {
                GetExamResult(
                    id = id,
                    title = title,
                    subTitle = subTitle,
                    description = description,
                    backgroundImage = backgroundImage,
                    iconImage = iconImage,
                    theme = theme,
                    quizIds = quizIds,
                    createdAt = createdAt
                )
            }
    }
}
