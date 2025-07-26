package com.gotchai.storage.rdb.badge.repository

import com.gotchai.storage.rdb.badge.entity.UserBadgeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserBadgeJpaRepository : JpaRepository<UserBadgeEntity, Long> {
    fun findAllByUserId(userId: Long): List<UserBadgeEntity>
}
