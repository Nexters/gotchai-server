package com.gotchai.storage.rdb.badge.repository

import com.gotchai.storage.rdb.badge.entity.UserBadgeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserBadgeJpaRepository : JpaRepository<UserBadgeEntity, Long> {
    fun findByBadgeIdAndUserId(
        badgeId: Long,
        userId: Long
    ): UserBadgeEntity?

    fun findAllByUserId(userId: Long): List<UserBadgeEntity>
}
