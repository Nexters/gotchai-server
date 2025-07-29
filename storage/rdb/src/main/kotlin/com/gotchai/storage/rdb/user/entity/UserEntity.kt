package com.gotchai.storage.rdb.user.entity

import com.gotchai.domain.user.entity.Role
import com.gotchai.domain.user.entity.User
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.*

@Table(name = "user")
@Entity
class UserEntity(
    @Column(length = 50)
    val email: String,
    val password: String?,
    @ElementCollection
    @Enumerated(EnumType.STRING)
    val roles: Set<Role>
) : BaseEntity() {
    companion object {
        fun from(creation: User.Creation): UserEntity =
            with(creation) {
                UserEntity(
                    email = email,
                    password = password,
                    roles = roles
                )
            }
    }

    fun toUser(): User =
        User(
            id = id!!,
            email = email,
            password = password,
            roles = roles,
            createdAt = createdAt
        )
}
