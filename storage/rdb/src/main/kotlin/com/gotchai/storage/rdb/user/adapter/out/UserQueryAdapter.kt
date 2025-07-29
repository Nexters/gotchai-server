package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class UserQueryAdapter(
    private val userRepository: UserJpaRepository
) : UserQueryPort {
    override fun getUserById(id: Long): User? =
        userRepository
            .findByIdOrNull(id)
            ?.toUser()

    override fun getUserByEmail(email: String): User? =
        userRepository
            .findByEmail(email)
            ?.toUser()
}
