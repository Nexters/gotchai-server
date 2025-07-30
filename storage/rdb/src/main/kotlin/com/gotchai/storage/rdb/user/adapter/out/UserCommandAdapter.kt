package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.entity.UserEntity
import com.gotchai.storage.rdb.user.repository.UserJpaRepository

@Adapter
class UserCommandAdapter(
    private val userRepository: UserJpaRepository
) : UserCommandPort {
    override fun createUser(creation: User.Creation): User =
        userRepository
            .save(UserEntity.from(creation))
            .toUser()
}
