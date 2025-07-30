package com.gotchai.storage.redis.auth.entity

import com.gotchai.domain.auth.entity.RefreshToken
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.Duration

@RedisHash
class RefreshTokenEntity(
    @Id
    val userId: Long,
    val deviceId: String?,
    val content: String,
    val expiration: Duration
) {
    @TimeToLive
    private var ttl = expiration.toSeconds()

    companion object {
        fun from(creation: RefreshToken.Creation): RefreshTokenEntity =
            with(creation) {
                RefreshTokenEntity(
                    userId = userId,
                    deviceId = deviceId,
                    content = content,
                    expiration = expiration
                )
            }
    }

    fun toRefreshToken(): RefreshToken =
        RefreshToken(
            userId = userId,
            deviceId = deviceId,
            content = content,
            expiration = expiration
        )
}
