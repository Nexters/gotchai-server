package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.UserSocial

interface UserSocialCommandPort {
    fun createUserSocial(creation: UserSocial.Creation): UserSocial

    fun withdrawal(userId: Long)
}
