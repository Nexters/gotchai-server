package com.gotchai.domain.user

interface UserRepository {
    fun save(gotchaiCreation: User.GotchaiCreation)

    fun findBy(userId: Long): User.Issue

    fun findCredentialByEmail(email: String): User.Credential

    fun existsByEmail(email: String): Boolean
}
