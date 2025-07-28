package com.gotchai.api.global.exception

class JwtException(
    override val message: String?,
) : RuntimeException(message)
