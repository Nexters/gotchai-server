package com.gotchai.storage.redis.auth.adapter.out

import com.gotchai.domain.auth.entity.RefreshToken
import com.gotchai.domain.auth.port.out.RefreshTokenQueryPort
import com.gotchai.storage.redis.auth.repository.RefreshTokenRedisRepository
import com.gotchai.storage.redis.global.annotation.Adapter
import org.springframework.data.repository.findByIdOrNull

@Adapter
class RefreshTokenQueryAdapter(
    private val refreshTokenRepository: RefreshTokenRedisRepository
) : RefreshTokenQueryPort {
    override fun getRefreshTokenByUserId(userId: Long): RefreshToken? =
        refreshTokenRepository
            .findByIdOrNull(userId)
            ?.toRefreshToken()
}
