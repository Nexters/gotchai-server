package com.gotchai.storage.rdb.badge.entity

import com.gotchai.domain.badge.entity.Rank
import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_badge")
class UserBadgeEntity(
    val userId: Long,
    val badgeId: Long,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", columnDefinition = "varchar(50)")
    val rank: Rank,
) : BaseEntity() {
    companion object {
        fun from(creation: UserBadge.Creation): UserBadgeEntity =
            with(creation) {
                UserBadgeEntity(
                    userId = userId,
                    badgeId = badgeId,
                    rank = rank
                )
            }
    }

    fun toUserBadge(): UserBadge =
        UserBadge(
            id = id!!,
            userId = userId,
            badgeId = badgeId,
            rank = rank,
            createdAt = createdAt,
        )
}
