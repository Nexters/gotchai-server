package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import org.springframework.stereotype.Service

@Service
class BadgeQueryService(
    private val badgeQueryPort: BadgeQueryPort,
    private val userBadgeQueryPort: UserBadgeQueryPort
) : BadgeQueryUseCase {
    override fun getBadgeById(badgeId: Long): Badge =
        badgeQueryPort.getBadgeById(badgeId)
            ?: throw BadgeNotFoundException()

    override fun getMyBadges(userId: Long): List<Badge> {
        val userBadges = userBadgeQueryPort.getUserBadgesByUserId(userId)
        val badges =
            badgeQueryPort
                .getBadgesByIdIn(userBadges.map { it.badgeId })

        return badges
    }

    override fun getBadgeByExamIdAndTier(
        examId: Long,
        tier: Tier
    ): Badge =
        badgeQueryPort.getBadgeByExamIdAndTier(examId, tier)
            ?: throw BadgeNotFoundException()
}
