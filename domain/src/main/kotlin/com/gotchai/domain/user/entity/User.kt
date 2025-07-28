package com.gotchai.domain.user.entity

import java.time.LocalDateTime

class User(
    val id: Long,
    val email: String,
    val password: String?,
    val roles: Set<Role>,
    val createdAt: LocalDateTime,
) {
    data class Creation(
        val email: String,
        val password: String?,
        val roles: Set<Role>,
    )
}
