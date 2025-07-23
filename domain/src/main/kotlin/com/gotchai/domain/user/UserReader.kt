package com.gotchai.domain.user

import org.springframework.stereotype.Component

@Component
class UserReader(
    private val userRepository: UserRepository,
) {
    fun readUserIssue(userId: Long): User.Issue = userRepository.readBy(userId)
}
