package com.gotchai.domain.user

import com.gotchai.common.enum.user.SocialType

data class SocialUser(
    val id: Long,
    val name: String,
    val socialId: String,
    val socialType: SocialType,
)
