package com.gotchai.domain.auth

import com.gotchai.common.enum.user.SocialType

data class CredentialSocial(
    val name: String,
    val email: String,
    val socialId: String,
    val socialType: SocialType,
)
