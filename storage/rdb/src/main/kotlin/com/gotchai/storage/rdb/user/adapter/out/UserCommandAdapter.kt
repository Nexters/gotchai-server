package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import com.gotchai.storage.rdb.user.entity.UserEntity
import com.gotchai.storage.rdb.user.repository.UserJpaRepository

@Adapter
class UserCommandAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserCommandPort {
    override fun createUser(creation: User.Creation): User =
        userJpaRepository
            .save(UserEntity.from(creation))
            .toUser()

    override fun deleteByUserId(userId: Long) {
        userJpaRepository
            .findByIdOrElseThrow(userId)
            .softDelete()
    }
}
