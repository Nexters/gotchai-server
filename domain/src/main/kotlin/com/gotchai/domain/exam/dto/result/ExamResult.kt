package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.exam.entity.ExamWithIsSolved
import java.time.LocalDateTime

data class ExamResult(
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
        fun from(examWithIsSolved: ExamWithIsSolved): ExamResult =
            with(examWithIsSolved) {
                ExamResult(
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
