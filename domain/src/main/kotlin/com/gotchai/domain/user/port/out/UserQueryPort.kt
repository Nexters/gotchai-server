package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.User

interface UserQueryPort {
    fun findAll(): List<User.Profile>

    fun findBy(userId: Long): User.Issue

    fun findBySocialId(socialId: String): User.Info?

    fun findCredentialByEmail(email: String): User.Credential

    fun existsByEmail(email: String): Boolean
}
