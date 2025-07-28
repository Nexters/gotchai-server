package com.gotchai.storage.redis.auth.repository

import com.gotchai.storage.redis.auth.entity.RefreshTokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRedisRepository : CrudRepository<RefreshTokenEntity, Long>
