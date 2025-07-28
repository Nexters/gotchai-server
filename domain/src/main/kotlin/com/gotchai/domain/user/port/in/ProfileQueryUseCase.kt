package com.gotchai.domain.user.port.`in`

import com.gotchai.domain.user.entity.Profile

interface ProfileQueryUseCase {
    fun getProfileByUserId(userId: Long): Profile?
}
