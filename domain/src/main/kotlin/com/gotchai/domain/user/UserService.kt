package com.gotchai.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userReader: UserReader,
) {
    fun getUserIssue(userId: Long): User.Issue = userReader.readUserIssue(userId)
}
