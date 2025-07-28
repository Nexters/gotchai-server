package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.entity.RefreshToken

interface RefreshTokenCommandPort {
    fun createRefreshToken(creation: RefreshToken.Creation): RefreshToken

    fun deleteRefreshTokenByUserId(userId: Long)
}
