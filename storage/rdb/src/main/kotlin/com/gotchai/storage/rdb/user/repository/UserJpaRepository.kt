package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findAllByDeletedAtIsNull(): List<UserEntity>

    fun findByEmail(email: String): UserEntity?

    fun findBySocialIdAndDeletedAtIsNull(socialId: String): UserEntity?

    fun existsByEmail(email: String): Boolean
}
