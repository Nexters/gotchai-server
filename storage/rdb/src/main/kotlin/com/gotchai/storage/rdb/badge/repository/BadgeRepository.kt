package com.gotchai.storage.rdb.badge.repository

import com.gotchai.storage.rdb.badge.entity.BadgeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BadgeRepository : JpaRepository<BadgeEntity, Long> {
    fun findAllByIdIn(ids: Collection<Long>): List<BadgeEntity>
}
