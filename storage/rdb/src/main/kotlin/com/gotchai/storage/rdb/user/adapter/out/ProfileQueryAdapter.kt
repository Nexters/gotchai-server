package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.Profile
import com.gotchai.domain.user.port.out.ProfileQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.repository.ProfileJpaRepository

@Adapter
class ProfileQueryAdapter(
    private val profileRepository: ProfileJpaRepository
) : ProfileQueryPort {
    override fun getProfileByUserId(userId: Long): Profile? =
        profileRepository
            .findByUserId(userId)
            ?.toProfile()

    override fun getProfiles(): List<Profile> =
        profileRepository
            .findAll()
            .map { it.toProfile() }
}
