package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import com.gotchai.storage.rdb.badge.repository.UserBadgeJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional

@Adapter
class UserBadgeQueryAdapter(
    private val userBadgeRepository: UserBadgeJpaRepository
) : UserBadgeQueryPort {
    @ReadOnlyTransactional
    override fun getUserBadgesByUserId(userId: Long): List<UserBadge> =
        userBadgeRepository
            .findAllByUserId(userId)
            .map { it.toUserBadge() }

    @ReadOnlyTransactional
    override fun getUserBadgeByBadgeIdAndUserId(
        badgeId: Long,
        userId: Long
    ): UserBadge? =
        userBadgeRepository
            .findByBadgeIdAndUserId(badgeId, userId)
            ?.toUserBadge()
}
