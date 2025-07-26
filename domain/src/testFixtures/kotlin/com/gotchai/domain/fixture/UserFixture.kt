package com.gotchai.domain.fixture

import com.gotchai.common.enum.user.SocialType
import com.gotchai.domain.user.User
import java.time.LocalDateTime

private const val NAME = "정상윤"
private const val EMAIL = "earlgrey02@apple.com"
private const val SOCIAL_ID = ""
private val SOCIAL_TYPE = SocialType.APPLE

fun createUser(
    id: Long = ID,
    name: String = NAME,
    email: String = EMAIL,
    socialId: String? = SOCIAL_ID,
    socialType: SocialType = SOCIAL_TYPE,
    createdAt: LocalDateTime = CREATED_AT,
): User.Info =
    User.Info(
        id = id,
        name = name,
        email = email,
        socialId = socialId,
        socialType = socialType,
        createdAt = createdAt,
    )
