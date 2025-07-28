package com.gotchai.storage.redis.auth.adapter.out

import com.gotchai.domain.auth.entity.RefreshToken
import com.gotchai.domain.auth.port.out.RefreshTokenCommandPort
import com.gotchai.storage.redis.auth.entity.RefreshTokenEntity
import com.gotchai.storage.redis.auth.repository.RefreshTokenRedisRepository
import com.gotchai.storage.redis.global.annotation.Adapter

@Adapter
class RefreshTokenCommandAdapter(
    private val refreshTokenRepository: RefreshTokenRedisRepository,
) : RefreshTokenCommandPort {
    override fun createRefreshToken(creation: RefreshToken.Creation): RefreshToken =
        refreshTokenRepository.save(RefreshTokenEntity.from(creation))
            .toRefreshToken()

    override fun deleteRefreshTokenByUserId(userId: Long) {
        refreshTokenRepository.deleteById(userId)
    }
}
