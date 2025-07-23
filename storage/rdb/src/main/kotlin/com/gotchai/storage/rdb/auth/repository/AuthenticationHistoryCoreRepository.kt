package com.gotchai.storage.rdb.auth.repository

import com.gotchai.common.enum.auth.AuthenticationEntityStatus
import com.gotchai.domain.auth.AuthenticationHistory
import com.gotchai.domain.auth.AuthenticationHistoryRepository
import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import com.gotchai.storage.rdb.auth.entity.AuthenticationHistoryEntity
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AuthenticationHistoryCoreRepository(
    private val repository: AuthenticationHistoryJpaRepository,
) : AuthenticationHistoryRepository {
    override fun create(newAuthenticationHistory: AuthenticationHistory.Creation): AuthenticationHistory.Info {
        val saveHistory = repository.save(AuthenticationHistoryEntity(newAuthenticationHistory))
        return saveHistory.toAuthenticationHistory()
    }

    @ReadOnlyTransactional
    override fun findUserIdWithDeviceWithRefreshToken(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info? {
        val histories =
            repository.findAllByUserIdAndDeviceId(
                userId = userId,
                deviceId = deviceId,
            )
        return histories.find { it.refreshToken == refreshToken }?.toAuthenticationHistory()
    }

    @Transactional
    override fun update(updateAuthenticationHistory: AuthenticationHistory.Update): AuthenticationHistory.Info? {
        val histories =
            repository.findAllByUserIdAndDeviceId(
                userId = updateAuthenticationHistory.userId,
                deviceId = updateAuthenticationHistory.deviceId,
            )
        return histories
            .find {
                it.refreshToken == updateAuthenticationHistory.refreshToken
            }?.updateRefreshToken(updateAuthenticationHistory.token)
    }

    @Transactional
    override fun removeToken(userId: Long): List<String>? =
        repository.findAllByUserIdAndEntityStatus(userId, AuthenticationEntityStatus.ACTIVE)?.map {
            it.delete()
            it.accessToken
        }

    override fun findUserId(userId: Long): AuthenticationHistory.Info? {
        val histories = repository.findAllByUserIdAndEntityStatus(userId, AuthenticationEntityStatus.ACTIVE)

        if (histories.isNullOrEmpty()) {
            return null
        }

        return histories.last().toAuthenticationHistory()
    }

    @Transactional
    override fun remove(token: String): String {
        val authenticationHistory =
            repository.findByAccessToken(token)
                ?: throw AuthenticationErrorException(AuthenticationErrorType.NOT_FOUND_HISTORY)
        authenticationHistory.delete()
        return authenticationHistory.refreshToken
    }
}
