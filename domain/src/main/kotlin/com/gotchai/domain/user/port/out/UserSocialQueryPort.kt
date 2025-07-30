package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.UserSocial

interface UserSocialQueryPort {
    fun getUserSocialBySocialId(socialId: String): UserSocial?
}
