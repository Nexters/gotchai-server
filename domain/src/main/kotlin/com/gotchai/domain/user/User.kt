package com.gotchai.domain.user

import com.gotchai.common.enum.user.SocialType
import java.time.LocalDateTime

class User {
    data class Issue(
        val id: Long,
    )

    data class GotchaiCreation(
        val name: String,
        val email: String,
        val password: String,
    )

    data class SocialCreation(
        val name: String,
        val email: String,
        val socialId: String,
        val socialType: SocialType,
    )

    data class Info(
        val id: Long,
        val name: String,
        val email: String,
        val socialId: String?,
        val socialType: SocialType,
        val createdAt: LocalDateTime,
    )

    data class Credential(
        val id: Long,
        val email: String,
        val password: String,
        val socialType: SocialType,
    )

    data class Profile(
        val name: String,
        val email: String,
        val socialType: SocialType,
    )
}
