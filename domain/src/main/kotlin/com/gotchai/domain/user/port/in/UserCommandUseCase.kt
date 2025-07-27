package com.gotchai.domain.user.port.`in`

import com.gotchai.domain.user.entity.User

interface UserCommandUseCase {
    fun create(create: User.SocialCreation): User.Info

    fun testSignUp(
        name: String,
        email: String,
        password: String,
    )
}
