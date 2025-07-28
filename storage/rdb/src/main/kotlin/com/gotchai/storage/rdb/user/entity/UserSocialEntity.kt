package com.gotchai.storage.rdb.user.entity

import com.gotchai.domain.user.entity.SocialProvider
import com.gotchai.domain.user.entity.UserSocial
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.*

@Table(name = "user_social")
@Entity
class UserSocialEntity(
    val userId: Long,
    val socialId: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", columnDefinition = "varchar(50)")
    private val provider: SocialProvider,
) : BaseEntity() {
    companion object {
        fun from(creation: UserSocial.Creation): UserSocialEntity =
            with(creation) {
                UserSocialEntity(
                    userId = userId,
                    socialId = socialId,
                    provider = provider
                )
            }
    }

    fun toUserSocial(): UserSocial =
        UserSocial(
            id = id!!,
            userId = userId,
            socialId = socialId,
            provider = provider,
            createdAt = createdAt
        )
}
