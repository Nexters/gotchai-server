package com.gotchai.storage.rdb.auth

import com.gotchai.domain.auth.AuthenticationHistory
import com.gotchai.domain.auth.token.Token
import com.gotchai.domain.auth.token.TokenStatus
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
        accessToken = newAuthenticationHistory.newToken.token.accessToken,
        refreshToken = newAuthenticationHistory.newToken.token.refreshToken,
    )

    fun toAuthenticationHistory(): AuthenticationHistory.Info =
        AuthenticationHistory.Info(
            authenticationId = id!!,
            deviceId = deviceId,
            token =
                Token(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                ),
            status = entityStatus.toTokenStatus(),
            loggedInAt = updatedAt ?: createdAt,
        )

    fun updateRefreshToken(token: Token): AuthenticationHistory.Info {
        this.accessToken = token.accessToken
        this.refreshToken = token.refreshToken
        return AuthenticationHistory.Info(
            authenticationId = id!!,
            deviceId = deviceId,
            token =
                Token(
                    accessToken = token.accessToken,
                    refreshToken = token.refreshToken,
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
