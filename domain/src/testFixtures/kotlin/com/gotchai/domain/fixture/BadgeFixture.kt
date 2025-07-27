package com.gotchai.domain.fixture

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Rank
import com.gotchai.domain.badge.entity.UserBadge
import java.time.LocalDateTime

private const val NAME = "AI 산타 감별사"
private const val DESCRIPTION = "크리스마스엔 선물보다 눈치가 중요하다는 걸 증명했어요!"
private const val IMAGE = "https://gotchai.com/image.png"
private val RANK = Rank.GOLD

fun createGetMyBadgeResult(
    id: Long = ID,
    examId: Long = ID,
    name: String = NAME,
    description: String = DESCRIPTION,
    image: String = IMAGE,
    rank: Rank = RANK,
    acquiredAt: LocalDateTime = CREATED_AT,
): GetMyBadgeResult =
    GetMyBadgeResult(
        id = id,
        examId = examId,
        name = name,
        description = description,
        image = image,
        rank = rank,
        acquiredAt = acquiredAt
    )

fun createBadge(
    id: Long = ID,
    examId: Long = ID,
    name: String = NAME,
    description: String = DESCRIPTION,
    image: String = IMAGE,
    rank: Rank = RANK,
    createdAt: LocalDateTime = CREATED_AT,
): Badge =
    Badge(
        id = id,
        examId = examId,
        name = name,
        description = description,
        image = image,
        rank = rank,
        createdAt = createdAt
    )

fun createUserBadge(
    id: Long = ID,
    userId: Long = ID,
    badgeId: Long = ID,
    createdAt: LocalDateTime = CREATED_AT,
): UserBadge =
    UserBadge(
        id = id,
        userId = userId,
        badgeId = badgeId,
        createdAt = createdAt
    )
