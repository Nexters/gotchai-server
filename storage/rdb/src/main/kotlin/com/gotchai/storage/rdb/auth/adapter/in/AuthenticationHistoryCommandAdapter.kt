package com.gotchai.storage.rdb.auth.adapter.`in`

import com.gotchai.domain.auth.entity.AuthenticationEntityStatus
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.exception.AuthenticationHistoryNotFoundException
import com.gotchai.domain.auth.port.out.AuthenticationHistoryCommandPort
import com.gotchai.storage.rdb.auth.entity.AuthenticationHistoryEntity
import com.gotchai.storage.rdb.auth.repository.AuthenticationHistoryJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class AuthenticationHistoryCommandAdapter(
    private val repository: AuthenticationHistoryJpaRepository,
) : AuthenticationHistoryCommandPort {
    override fun create(newAuthenticationHistory: AuthenticationHistory.Creation): AuthenticationHistory.Info {
        val saveHistory = repository.save(AuthenticationHistoryEntity(newAuthenticationHistory))
        return saveHistory.toAuthenticationHistory()
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
            }?.updateRefreshToken(updateAuthenticationHistory.tokenPair)
    }

    @Transactional
    override fun removeToken(userId: Long): List<String> =
        repository.findAllByUserIdAndEntityStatus(userId, AuthenticationEntityStatus.ACTIVE).map {
            it.delete()
            it.accessToken
        }

    @Transactional
    override fun remove(token: String): String {
        val authenticationHistory =
            repository.findByAccessToken(token)
                ?: throw AuthenticationHistoryNotFoundException()
        authenticationHistory.delete()
        return authenticationHistory.refreshToken
    }
}
