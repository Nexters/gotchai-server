package com.gotchai.domain.auth.dto

data class ProviderDetail(
    val userId: Long,
    val grantedAuthorities: List<String>,
)
