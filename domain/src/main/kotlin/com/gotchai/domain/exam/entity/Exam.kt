package com.gotchai.domain.exam.entity

import java.time.LocalDateTime

data class Exam(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val backgroundImage: String,
    val iconImage: String,
    val theme: String,
    val createdAt: LocalDateTime
) {
    data class Creation(
        val title: String,
        val subTitle: String,
        val description: String,
        val backgroundImage: String,
        val iconImage: String,
        val theme: String,
    )
}
