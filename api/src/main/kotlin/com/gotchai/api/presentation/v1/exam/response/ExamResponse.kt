package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.ExamResult
import com.gotchai.domain.exam.entity.Exam
import java.time.LocalDateTime

data class ExamResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val prompt: String,
    val backgroundImage: String,
    val iconImage: String,
    val coverImage: String,
    val theme: String,
    val isSolved: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(exam: Exam): ExamResponse =
            with(exam) {
                ExamResponse(
                    id = id,
                    title = title,
                    subTitle = subTitle,
                    description = description,
                    prompt = prompt,
                    backgroundImage = backgroundImage,
                    iconImage = iconImage,
                    coverImage = coverImage,
                    theme = theme,
                    isSolved = false,
                    createdAt = createdAt
                )
            }

        fun from(result: ExamResult): ExamResponse =
            with(result) {
                ExamResponse(
                    id = id,
                    title = title,
                    subTitle = subTitle,
                    description = description,
                    prompt = prompt,
                    backgroundImage = backgroundImage,
                    iconImage = iconImage,
                    coverImage = coverImage,
                    theme = theme,
                    isSolved = isSolved,
                    createdAt = createdAt
                )
            }
    }
}
