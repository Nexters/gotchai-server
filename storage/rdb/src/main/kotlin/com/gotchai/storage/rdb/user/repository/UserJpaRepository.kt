package com.gotchai.storage.rdb.user.repository

import com.gotchai.storage.rdb.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long>
