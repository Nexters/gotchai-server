package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.Provider

interface TokenQueryPort {
    fun findBy(accessToken: String): Provider?
}
