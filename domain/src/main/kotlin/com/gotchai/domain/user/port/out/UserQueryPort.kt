package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.User

interface UserQueryPort {
    fun getUserById(id: Long): User?

    fun getUserByEmail(email: String): User?
}
