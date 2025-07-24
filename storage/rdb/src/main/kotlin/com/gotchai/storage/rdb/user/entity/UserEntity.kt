package com.gotchai.storage.rdb.user.entity

import com.gotchai.common.enum.user.SocialType
import com.gotchai.domain.user.User
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Table
@Entity(name = "user")
class UserEntity(
    @Column(length = 20)
    val name: String,
    @Column(length = 50)
    val email: String,
    val password: String,
    val socialId: String?,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", columnDefinition = "varchar(50)")
    private val socialType: SocialType,
) : BaseEntity() {
    constructor(create: User.SocialCreation) : this(
        name = create.name,
        email = create.email,
        password = "",
        socialId = create.socialId,
        socialType = create.socialType,
    )

    constructor(create: User.GotchaiCreation) : this(
        name = create.name,
        email = create.email,
        password = create.password,
        socialId = null,
        socialType = SocialType.GOTCHAI,
    )

    fun toUserInfo(): User.Info =
        User.Info(
            id = id!!,
            name = name,
            email = email,
            socialId = socialId,
            socialType = socialType,
            createdAt = createdAt,
        )

    fun toCredential(): User.Credential =
        User.Credential(
            id = id!!,
            email = email,
            password = password,
            socialType = socialType,
        )

    fun toProfile(): User.Profile =
        User.Profile(
            name = name,
            email = email,
            socialType = socialType,
        )

    fun toIssue(): User.Issue =
        User.Issue(
            id = id!!,
        )
}
