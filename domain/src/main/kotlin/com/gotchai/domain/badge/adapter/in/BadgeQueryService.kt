package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BadgeQueryService(
    private val badgeQueryPort: BadgeQueryPort,
    private val userBadgeQueryPort: UserBadgeQueryPort
) : BadgeQueryUseCase {
    @Transactional(readOnly = true)
    override fun getBadgeById(badgeId: Long): Badge =
        badgeQueryPort.getBadgeById(badgeId)
            ?: throw BadgeNotFoundException()

    @Transactional(readOnly = true)
    override fun getMyBadges(userId: Long): List<GetMyBadgeResult> =
        badgeQueryPort
            .getBadgeWithAcquiredAtProjectionsByUserId(userId)
            .map { GetMyBadgeResult.from(it) }

    @Transactional(readOnly = true)
    override fun getBadgeByExamIdAndTier(
        examId: Long,
        tier: Tier
    ): Badge =
        badgeQueryPort.getBadgeByExamIdAndTier(examId, tier)
            ?: throw BadgeNotFoundException()
}
