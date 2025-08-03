package com.gotchai.storage.rdb.badge.repository

import com.gotchai.domain.badge.entity.Tier
import com.gotchai.storage.rdb.badge.entity.BadgeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BadgeJpaRepository : JpaRepository<BadgeEntity, Long> {
    fun findAllByIdIn(ids: Collection<Long>): List<BadgeEntity>

    fun findByExamIdAndTier(
        examId: Long,
        badgeTier: Tier
    ): BadgeEntity?
}
