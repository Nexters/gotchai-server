package com.gotchai.domain.user.port.out

import com.gotchai.domain.user.entity.Profile

interface ProfileQueryPort {
    fun getProfileByUserId(userId: Long): Profile?

    fun getProfiles(): List<Profile>
}
