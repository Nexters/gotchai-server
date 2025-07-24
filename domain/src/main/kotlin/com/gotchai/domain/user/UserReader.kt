package com.gotchai.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository,
) {
    fun readAll(): List<User.Profile> = userRepository.findAll()

    fun readUserIssue(userId: Long): User.Issue = userRepository.findBy(userId)

    fun readBySocialId(socialId: String): User.Info? = userRepository.findBySocialId(socialId)

    fun readCredentialByEmail(email: String): User.Credential = userRepository.findCredentialByEmail(email)
}
