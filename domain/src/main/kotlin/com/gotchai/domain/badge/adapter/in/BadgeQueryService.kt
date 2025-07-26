package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import org.springframework.stereotype.Service

@Service
class BadgeQueryService(
    private val badgeQueryPort: BadgeQueryPort,
    private val userBadgeQueryPort: UserBadgeQueryPort,
) : BadgeQueryUseCase {
    override fun getBadgeById(id: Long): Badge =
        badgeQueryPort.getBadgeById(id)
            ?: throw BadgeNotFoundException()

    override fun getMyBadges(userId: Long): List<GetMyBadgeResult> {
        val userBadges = userBadgeQueryPort.getUserBadgesByUserId(userId)
        val badges =
            badgeQueryPort.getBadgesByIdIn(userBadges.map { it.badgeId })
                .associateBy { it.id }

        return userBadges.mapNotNull { userBadge ->
            badges[userBadge.badgeId]?.let { badge ->
                GetMyBadgeResult.of(badge, userBadge)
            }
        }
    }
}
