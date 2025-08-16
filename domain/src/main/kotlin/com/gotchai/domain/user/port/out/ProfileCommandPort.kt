package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.Profile

interface ProfileCommandPort {
    fun createProfile(creation: Profile.Creation): Profile

    fun deleteByUserId(userId: Long)
}
