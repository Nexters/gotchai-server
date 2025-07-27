package com.gotchai.domain.auth.dto

import com.gotchai.domain.user.entity.SocialType

data class CredentialSocial(
    val name: String,
    val email: String,
    val socialId: String,
    val socialType: SocialType,
)
