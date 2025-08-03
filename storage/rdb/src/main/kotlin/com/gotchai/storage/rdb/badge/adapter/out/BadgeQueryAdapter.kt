package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.storage.rdb.badge.repository.BadgeJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import org.springframework.data.repository.findByIdOrNull

@Adapter
class BadgeQueryAdapter(
    private val badgeRepository: BadgeJpaRepository
) : BadgeQueryPort {
    @ReadOnlyTransactional
    override fun getBadgeById(id: Long): Badge? =
        badgeRepository
            .findByIdOrNull(id)
            ?.toBadge()

    @ReadOnlyTransactional
    override fun getBadgesByIdIn(ids: Collection<Long>): List<Badge> =
        badgeRepository
            .findAllByIdIn(ids)
            .map { it.toBadge() }

    @ReadOnlyTransactional
    override fun getBadgeByExamIdAndTier(
        examId: Long,
        badgeTier: Tier
    ): Badge? =
        badgeRepository
            .findByExamIdAndTier(examId, badgeTier)
            ?.toBadge()
}
