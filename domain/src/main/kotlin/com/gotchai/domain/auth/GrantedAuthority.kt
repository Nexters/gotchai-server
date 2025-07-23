package com.gotchai.domain.auth

import com.gotchai.common.enum.auth.AuthorityType

data class GrantedAuthority(
    val authorityType: AuthorityType,
)
