package com.gotchai.storage.rdb.auth.adapter.`in`

import com.gotchai.domain.auth.entity.AuthenticationEntityStatus
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.port.out.AuthenticationHistoryQueryPort
import com.gotchai.storage.rdb.auth.repository.AuthenticationHistoryJpaRepository
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import org.springframework.stereotype.Repository

@Repository
class AuthenticationHistoryQueryAdapter(
    private val repository: AuthenticationHistoryJpaRepository,
) : AuthenticationHistoryQueryPort {
    @ReadOnlyTransactional
    override fun findUserIdWithDeviceWithRefreshToken(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory? {
        val histories =
            repository.findAllByUserIdAndDeviceId(
                userId = userId,
                deviceId = deviceId,
            )
        return histories.find { it.refreshToken == refreshToken }?.toAuthenticationHistory()
    }

    @ReadOnlyTransactional
    override fun findUserId(userId: Long): AuthenticationHistory? {
        val histories = repository.findAllByUserIdAndEntityStatus(userId, AuthenticationEntityStatus.ACTIVE)

        if (histories.isNullOrEmpty()) {
            return null
        }

        return histories.last().toAuthenticationHistory()
    }
}
