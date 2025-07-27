package com.gotchai.storage.rdb.user.adapter.`in`

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserCommandPort
import com.gotchai.storage.rdb.user.entity.UserEntity
import com.gotchai.storage.rdb.user.repository.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserCommandAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserCommandPort {
    override fun save(gotchaiCreation: User.GotchaiCreation): User.Info =
        userJpaRepository
            .save(
                UserEntity(
                    gotchaiCreation,
                ),
            ).toUserInfo()

    override fun save(socialCreation: User.SocialCreation): User.Info =
        userJpaRepository
            .save(
                UserEntity(
                    socialCreation,
                ),
            ).toUserInfo()
}
