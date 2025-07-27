package com.gotchai.domain.user.dto

import com.gotchai.domain.user.entity.SocialType

data class SocialUser(
    val id: Long,
    val name: String,
    val socialId: String,
    val socialType: SocialType,
)
