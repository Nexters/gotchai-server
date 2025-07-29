package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.UserSocialEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserSocialJpaRepository : JpaRepository<UserSocialEntity, Long> {
    fun findBySocialId(socialId: String): UserSocialEntity?
}
