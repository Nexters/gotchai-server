package com.gotchai.domain.user

interface UserRepository {
    fun readBy(userId: Long): User.Issue
}
