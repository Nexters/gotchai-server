package com.gotchai.storage.rdb.auth
import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationHistoryJpaRepository : JpaRepository<AuthenticationHistoryEntity, Long> {
    fun findAllByUserIdAndDeviceId(
        userId: Long,
        deviceId: String?,
    ): List<AuthenticationHistoryEntity>

    fun findAllByUserIdAndEntityStatus(
        userId: Long,
        active: AuthenticationEntityStatus,
    ): List<AuthenticationHistoryEntity>?

    fun findByAccessToken(accessToken: String): AuthenticationHistoryEntity?
}
