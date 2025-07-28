package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.User

interface UserCommandPort {
    fun createUser(creation: User.Creation): User
}
