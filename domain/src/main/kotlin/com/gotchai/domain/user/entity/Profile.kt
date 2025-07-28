package com.gotchai.domain.user.entity

import java.time.LocalDateTime

class Profile(
    val id: Long,
    val userId: Long,
    val nickname: String,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val userId: Long,
        val nickname: String,
    )
}
