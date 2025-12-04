package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByIdAndDeletedAtIsNull(id: Long): UserEntity?

    fun findByEmailAndDeletedAtIsNull(email: String): UserEntity?
}
