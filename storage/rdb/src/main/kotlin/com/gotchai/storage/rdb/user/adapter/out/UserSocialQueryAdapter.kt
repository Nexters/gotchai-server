package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.UserSocial
import com.gotchai.domain.user.port.out.UserSocialQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.gotchai.storage.rdb.user.repository.UserSocialJpaRepository

@Adapter
class UserSocialQueryAdapter(
    private val userSocialJpaRepository: UserSocialJpaRepository
) : UserSocialQueryPort {
    @ReadOnlyTransactional
    override fun getUserSocialBySocialId(socialId: String): UserSocial? =
        userSocialJpaRepository
            .findBySocialIdAndDeletedAtIsNull(socialId)
            ?.toUserSocial()
}
