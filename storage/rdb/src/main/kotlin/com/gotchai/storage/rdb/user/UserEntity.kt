package com.gotchai.storage.rdb.user

import com.gotchai.common.enum.user.SocialType
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Table
@Entity(name = "users")
class UserEntity(
    @Column(length = 20)
    val name: String,
    @Column(length = 50)
    val email: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name= "provider", columnDefinition = "varchar(50)")
    private val socialType: SocialType,
): BaseEntity() {
}