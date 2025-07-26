package com.gotchai.storage.rdb.badge.entity

import com.gotchai.domain.badge.entity.Rank
import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "user_badge")
class UserBadgeEntity(
    val userId: Long,
    val badgeId: Long,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rank", columnDefinition = "varchar(20)")
    val rank: Rank,
) : BaseEntity() {
    companion object {
        fun from(creation: UserBadge.Creation): UserBadgeEntity =
            with(creation) {
                UserBadgeEntity(
                    userId = userId,
                    badgeId = badgeId,
                    rank = rank,
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
