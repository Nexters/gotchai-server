package com.gotchai.storage.rdb.auth.adapter.out

import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.port.out.AuthenticationHistoryCommandPort
import com.gotchai.storage.rdb.auth.entity.AuthenticationHistoryEntity
import com.gotchai.storage.rdb.auth.repository.AuthenticationHistoryJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class AuthenticationHistoryCommandAdapter(
    private val authenticationHistoryRepository: AuthenticationHistoryJpaRepository,
) : AuthenticationHistoryCommandPort {
    override fun createAuthenticationHistory(creation: AuthenticationHistory.Creation): AuthenticationHistory =
        authenticationHistoryRepository.save(AuthenticationHistoryEntity.from(creation))
            .toAuthenticationHistory()

    //    @Transactional
    //    override fun update(updateAuthenticationHistory: AuthenticationHistory.Update): AuthenticationHistory? {
    //        val histories =
    //            repository.findAllByUserIdAndDeviceId(
    //                userId = updateAuthenticationHistory.userId,
    //                deviceId = updateAuthenticationHistory.deviceId,
    //            )
    //        return histories
    //            .find {
    //                it.refreshToken == updateAuthenticationHistory.refreshToken
    //            }?.updateRefreshToken(updateAuthenticationHistory.tokenPair)
    //    }
    //
    //    @Transactional
    //    override fun removeToken(userId: Long): List<String> =
    //        repository.findAllByUserIdAndEntityStatus(userId, AuthenticationEntityStatus.ACTIVE).map {
    //            it.delete()
    //            it.accessToken
    //        }
    //
    //    @Transactional
    //    override fun remove(token: String): String {
    //        val authenticationHistory =
    //            repository.findByAccessToken(token)
    //                ?: throw AuthenticationHistoryNotFoundException()
    //        authenticationHistory.delete()
    //        return authenticationHistory.refreshToken
    //    }
}
