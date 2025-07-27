package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.Token
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User

interface TokenCommandPort {
    fun create(
        deviceId: String?,
        user: User.Issue,
    ): Token

    fun create(
        deviceId: String?,
        socialUser: SocialUser,
    ): Token

    fun refresh(refreshToken: String): Token

    fun remove(token: String): String

    fun removeByUserId(userId: Long)
}
