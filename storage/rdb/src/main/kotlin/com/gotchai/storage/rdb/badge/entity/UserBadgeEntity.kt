package com.gotchai.storage.rdb.badge.entity

import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_badge")
class UserBadgeEntity(
    val userId: Long,
    val badgeId: Long,
) : BaseEntity() {
    companion object {
        fun from(creation: UserBadge.Creation): UserBadgeEntity =
            with(creation) {
                UserBadgeEntity(
                    userId = userId,
                    badgeId = badgeId,
                )
            }
    }

    fun toUserBadge(): UserBadge =
        UserBadge(
            id = id!!,
            userId = userId,
            badgeId = badgeId,
            createdAt = createdAt,
        )
}
