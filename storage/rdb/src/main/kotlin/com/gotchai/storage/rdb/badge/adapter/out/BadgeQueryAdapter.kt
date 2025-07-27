package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.storage.rdb.badge.repository.BadgeJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BadgeQueryAdapter(
    private val badgeRepository: BadgeJpaRepository,
) : BadgeQueryPort {
    override fun getBadgeById(id: Long): Badge? =
        badgeRepository.findByIdOrNull(id)
            ?.toBadge()

    override fun getBadgesByIdIn(ids: Collection<Long>): List<Badge> =
        badgeRepository.findAllByIdIn(ids)
            .map { it.toBadge() }
}
