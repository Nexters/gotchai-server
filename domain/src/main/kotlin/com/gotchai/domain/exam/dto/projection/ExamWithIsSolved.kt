package com.gotchai.domain.exam.dto.projection

import java.time.LocalDateTime

data class ExamWithIsSolved(
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
)
