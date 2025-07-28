package com.gotchai.storage.rdb.auth.entity

import com.gotchai.domain.auth.entity.AuthenticationEntityStatus
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.entity.TokenStatus
import com.gotchai.storage.rdb.global.entity.AuthenticationBaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "authentication_history")
class AuthenticationHistoryEntity(
    val userId: Long,
    val deviceId: String?,
) : AuthenticationBaseEntity() {
    companion object {
        fun from(creation: AuthenticationHistory.Creation): AuthenticationHistoryEntity =
            with(creation) {
                AuthenticationHistoryEntity(
                    userId = userId,
                    deviceId = deviceId,
                )
            }
    }

    fun toAuthenticationHistory(): AuthenticationHistory =
        AuthenticationHistory(
            id = id!!,
            userId = userId,
            deviceId = deviceId,
            status = entityStatus.toTokenStatus(),
            loggedInAt = updatedAt ?: createdAt,
        )

    internal fun AuthenticationEntityStatus.toTokenStatus(): TokenStatus =
        when (this) {
            AuthenticationEntityStatus.ACTIVE -> TokenStatus.ACTIVE
            AuthenticationEntityStatus.DELETE -> TokenStatus.INACTIVE
        }
}
