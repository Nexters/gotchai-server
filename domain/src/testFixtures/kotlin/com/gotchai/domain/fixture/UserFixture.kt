package com.gotchai.domain.fixture

import com.gotchai.domain.user.entity.SocialProvider
import com.gotchai.domain.user.entity.User
import java.time.LocalDateTime

private const val NAME = "정상윤"
private const val EMAIL = "earlgrey02@apple.com"
private const val SOCIAL_ID = "asdakjsadkkl"
private val SOCIAL_TYPE = SocialProvider.APPLE

fun createUser(
    id: Long = ID,
    name: String = NAME,
    email: String = EMAIL,
    socialId: String? = SOCIAL_ID,
    socialType: SocialProvider = SOCIAL_TYPE,
    createdAt: LocalDateTime = CREATED_AT,
): User =
    User(
        id = id,
        name = name,
        email = email,
        socialId = socialId,
        socialType = socialType,
        createdAt = createdAt,
    )
