package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import com.gotchai.storage.rdb.badge.entity.UserBadgeEntity
import com.gotchai.storage.rdb.badge.repository.UserBadgeJpaRepository
import org.springframework.stereotype.Component

@Component
class UserBadgeQueryAdapter(
    private val userBadgeRepository: UserBadgeJpaRepository,
) : UserBadgeQueryPort {
    override fun getUserBadgesByUserId(userId: Long): List<UserBadge> =
        userBadgeRepository.findAllByUserId(userId)
            .map(UserBadgeEntity::toUserBadge)
}
