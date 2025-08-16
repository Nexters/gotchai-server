package com.gotchai.domain.fixture

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAt
import com.gotchai.domain.badge.dto.result.GetMyBadgesResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import java.time.LocalDateTime

const val NAME = "AI 산타 감별사"
const val DESCRIPTION_BADGE = "크리스마스엔 선물보다 눈치가 중요하다는 걸 증명했어요!"
const val IMAGE = "https://gotchai.com/image.png"
val TIER = Tier.GOLD
const val TOTAL_BADGE_COUNT = 5L

fun createBadge(
    id: Long = ID,
    examId: Long = ID,
    name: String = NAME,
    description: String = DESCRIPTION_BADGE,
    image: String = IMAGE,
    tier: Tier = TIER,
    createdAt: LocalDateTime = CREATED_AT
): Badge =
    Badge(
        id = id,
        examId = examId,
        name = name,
        description = description,
        image = image,
        tier = tier,
        createdAt = createdAt
    )

fun createUserBadge(
    id: Long = ID,
    userId: Long = ID,
    badgeId: Long = ID,
    createdAt: LocalDateTime = CREATED_AT
): UserBadge =
    UserBadge(
        id = id,
        userId = userId,
        badgeId = badgeId,
        createdAt = createdAt
    )

fun createBadgeWithAcquiredAt(
    id: Long = ID,
    examId: Long = ID,
    name: String = NAME,
    description: String = DESCRIPTION_BADGE,
    image: String = IMAGE,
    tier: Tier = TIER,
    createdAt: LocalDateTime = CREATED_AT,
    acquiredAt: LocalDateTime = CREATED_AT
): BadgeWithAcquiredAt =
    BadgeWithAcquiredAt(
        id = id,
        examId = examId,
        name = name,
        description = description,
        image = image,
        tier = tier,
        createdAt = createdAt,
        acquiredAt = acquiredAt
    )

fun createGetMyBadgesResult(
    badges: List<BadgeWithAcquiredAt> = listOf(createBadgeWithAcquiredAt()),
    totalBadgeCount: Long = TOTAL_BADGE_COUNT
): GetMyBadgesResult =
    GetMyBadgesResult(
        badges = badges,
        totalBadgeCount = totalBadgeCount
    )
