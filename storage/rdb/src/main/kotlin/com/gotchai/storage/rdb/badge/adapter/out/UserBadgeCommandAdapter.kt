package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.port.out.UserBadgeCommandPort
import com.gotchai.storage.rdb.badge.entity.UserBadgeEntity
import com.gotchai.storage.rdb.badge.repository.UserBadgeJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class UserBadgeCommandAdapter(
    private val userBadgeJpaRepository: UserBadgeJpaRepository
) : UserBadgeCommandPort {
    override fun createUserBadge(creation: UserBadge.Creation): UserBadge =
        userBadgeJpaRepository.save(UserBadgeEntity.from(creation)).toUserBadge()
}
