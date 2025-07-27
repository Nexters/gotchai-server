package com.gotchai.domain.exam.entity

import java.time.LocalDateTime

class Exam {
    data class Creation(
        val title: String,
        val subTitle: String,
        val descriptionImage: String,
        val iconImage: String,
        val theme: String,
    )

    data class Info(
        val id: Long,
        val title: String,
        val subTitle: String,
        val descriptionImage: String,
        val iconImage: String,
        val theme: String,
        val createdAt: LocalDateTime,
    )
}
