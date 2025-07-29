package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.entity.RefreshToken

interface RefreshTokenQueryPort {
    fun getRefreshTokenByUserId(userId: Long): RefreshToken?
}
