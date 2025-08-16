package com.gotchai.domain.fixture

import com.gotchai.domain.user.dto.result.GetUserRankingResult
import com.gotchai.domain.user.entity.*
import java.time.LocalDateTime

const val EMAIL = "earlgrey02@apple.com"
const val PASSWORD = "root"
val ROLES = setOf(Role.MEMBER)
const val NICKNAME = "우울한 얼그레이 #1"
const val SOCIAL_ID = "asdakjsadkkl"
val PROVIDER = SocialProvider.APPLE

fun createUser(
    id: Long = ID,
    email: String = EMAIL,
    password: String? = PASSWORD,
    roles: Set<Role> = ROLES,
    createdAt: LocalDateTime = CREATED_AT
): User =
    User(
        id = id,
        email = email,
        password = password,
        roles = roles,
        createdAt = createdAt
    )

fun createProfile(
    id: Long = ID,
    userId: Long = ID,
    nickname: String = NICKNAME,
    createdAt: LocalDateTime = CREATED_AT
): Profile =
    Profile(
        id = id,
        userId = userId,
        nickname = nickname,
        createdAt = createdAt
    )

fun createUserSocial(
    id: Long = ID,
    userId: Long = ID,
    socialId: String = SOCIAL_ID,
    provider: SocialProvider = PROVIDER,
    createdAt: LocalDateTime = CREATED_AT
): UserSocial =
    UserSocial(
        id = id,
        userId = userId,
        socialId = socialId,
        provider = provider,
        createdAt = createdAt
    )

fun createGetUserRankingResult(
    name: String = NICKNAME,
    rating: Int = 25
): GetUserRankingResult =
    GetUserRankingResult(
        name = name,
        rating = rating
    )
