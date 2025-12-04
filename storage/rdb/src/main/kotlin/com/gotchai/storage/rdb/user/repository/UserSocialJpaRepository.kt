package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.UserSocialEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserSocialJpaRepository : JpaRepository<UserSocialEntity, Long> {
    fun findByUserId(userId: Long): UserSocialEntity?

    fun findBySocialIdAndDeletedAtIsNull(socialId: String): UserSocialEntity?
}
