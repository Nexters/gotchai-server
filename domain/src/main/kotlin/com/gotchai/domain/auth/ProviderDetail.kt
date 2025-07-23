package com.gotchai.domain.auth

data class ProviderDetail(
    val userId: Long,
    val grantedAuthorities: List<String>,
)
