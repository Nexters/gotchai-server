package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileJpaRepository : JpaRepository<ProfileEntity, Long> {
    fun findByUserId(userId: Long): ProfileEntity?

    fun findByUserIdAndDeletedAtIsNull(userId: Long): ProfileEntity?
}
