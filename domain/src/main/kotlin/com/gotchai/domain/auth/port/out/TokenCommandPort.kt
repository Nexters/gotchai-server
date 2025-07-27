package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User

interface TokenCommandPort {
    fun create(
        deviceId: String?,
        user: User.Issue,
    ): TokenPair

    fun create(
        deviceId: String?,
        socialUser: SocialUser,
    ): TokenPair

    fun refresh(refreshToken: String): TokenPair

    fun remove(token: String): String

    fun removeByUserId(userId: Long)
}
