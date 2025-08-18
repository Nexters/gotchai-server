package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.gotchai.storage.rdb.user.repository.UserJpaRepository

@Adapter
class UserQueryAdapter(
    private val userRepository: UserJpaRepository
) : UserQueryPort {
    @ReadOnlyTransactional
    override fun getUserById(id: Long): User? =
        userRepository
            .findByIdAndDeletedAtIsNull(id)
            ?.toUser()

    @ReadOnlyTransactional
    override fun getUserByEmail(email: String): User? =
        userRepository
            .findByEmailAndDeletedAtIsNull(email)
            ?.toUser()
}
