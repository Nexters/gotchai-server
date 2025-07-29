package com.gotchai.storage.rdb.user.entity

import com.gotchai.domain.user.entity.Profile
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "profile")
@Entity
class ProfileEntity(
    val userId: Long,
    @Column(length = 20)
    val nickname: String
) : BaseEntity() {
    companion object {
        fun from(creation: Profile.Creation): ProfileEntity =
            with(creation) {
                ProfileEntity(
                    userId = userId,
                    nickname = nickname
                )
            }
    }

    fun toProfile(): Profile =
        Profile(
            id = id!!,
            userId = userId,
            nickname = nickname,
            createdAt = createdAt
        )
}
