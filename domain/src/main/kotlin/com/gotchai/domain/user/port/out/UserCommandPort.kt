package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.User

interface UserCommandPort {
    fun save(gotchaiCreation: User.GotchaiCreation): User.Info

    fun save(socialCreation: User.SocialCreation): User.Info
}
