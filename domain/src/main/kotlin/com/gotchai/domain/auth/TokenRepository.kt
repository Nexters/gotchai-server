package com.gotchai.domain.auth

import com.gotchai.domain.user.SocialUser
import com.gotchai.domain.user.User

interface TokenRepository {
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

    fun findBy(accessToken: String): Provider?
}
