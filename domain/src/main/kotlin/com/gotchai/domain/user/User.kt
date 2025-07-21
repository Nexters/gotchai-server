package com.gotchai.domain.user

import com.gotchai.common.enum.user.SocialType
import java.time.LocalDateTime

class User {
    data class Create(
        val name: String,
        val email: String,
        val socialType: SocialType
    )
    data class Info(
        val id: Long,
        val name: String,
        val email: String,
        val socialType: SocialType,
        val createdAt: LocalDateTime,
    )
}