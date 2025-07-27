package com.gotchai.storage.rdb.auth.entity

import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.auth.entity.AuthenticationEntityStatus
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.entity.TokenStatus
import com.gotchai.storage.rdb.global.common.AuthenticationBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "authentication_history")
class AuthenticationHistoryEntity(
    val userId: Long,
    val deviceId: String?,
    @Column(columnDefinition = "TEXT")
    var accessToken: String,
    @Column(columnDefinition = "TEXT")
    var refreshToken: String,
) : AuthenticationBaseEntity() {
    constructor(
        newAuthenticationHistory: AuthenticationHistory.Creation,
    ) : this(
        userId = newAuthenticationHistory.userId,
        deviceId = newAuthenticationHistory.deviceId,
        accessToken = newAuthenticationHistory.tokenPair.accessToken,
        refreshToken = newAuthenticationHistory.tokenPair.refreshToken,
    )

    fun toAuthenticationHistory(): AuthenticationHistory =
        AuthenticationHistory(
            id = id!!,
            deviceId = deviceId,
            tokenPair =
                TokenPair(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                ),
            status = entityStatus.toTokenStatus(),
            loggedInAt = updatedAt ?: createdAt,
        )

    fun updateRefreshToken(tokenPair: TokenPair): AuthenticationHistory {
        this.accessToken = tokenPair.accessToken
        this.refreshToken = tokenPair.refreshToken
        return AuthenticationHistory(
            id = id!!,
            deviceId = deviceId,
            tokenPair =
                TokenPair(
                    accessToken = tokenPair.accessToken,
                    refreshToken = tokenPair.refreshToken,
                ),
            status = entityStatus.toTokenStatus(),
            loggedInAt = updatedAt ?: createdAt,
        )
    }

    internal fun AuthenticationEntityStatus.toTokenStatus(): TokenStatus =
        when (this) {
            AuthenticationEntityStatus.ACTIVE -> TokenStatus.ACTIVE
            AuthenticationEntityStatus.DELETE -> TokenStatus.INACTIVE
        }
}
