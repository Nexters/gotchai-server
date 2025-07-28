package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.UserSocial
import com.gotchai.domain.user.port.out.UserSocialCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.entity.UserSocialEntity
import com.gotchai.storage.rdb.user.repository.UserSocialJpaRepository

@Adapter
class UserSocialCommandAdapter(
    private val userSocialRepository: UserSocialJpaRepository,
) : UserSocialCommandPort {
    override fun createUserSocial(creation: UserSocial.Creation): UserSocial =
        userSocialRepository.save(UserSocialEntity.from(creation))
            .toUserSocial()
}
