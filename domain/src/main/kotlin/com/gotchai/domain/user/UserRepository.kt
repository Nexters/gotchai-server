package com.gotchai.domain.user

interface UserRepository {
    fun save(gotchaiCreation: User.GotchaiCreation): User.Info

    fun save(socialCreation: User.SocialCreation): User.Info

    fun findAll(): List<User.Profile>

    fun findBy(userId: Long): User.Issue

    fun findBySocialId(socialId: String): User.Info?

    fun findCredentialByEmail(email: String): User.Credential

    fun existsByEmail(email: String): Boolean
}
