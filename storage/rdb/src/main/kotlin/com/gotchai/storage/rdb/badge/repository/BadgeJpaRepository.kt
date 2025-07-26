package com.gotchai.storage.rdb.badge.repository

import com.gotchai.storage.rdb.badge.entity.BadgeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BadgeJpaRepository : JpaRepository<BadgeEntity, Long> {
    fun findAllByIdIn(ids: Collection<Long>): List<BadgeEntity>
}
