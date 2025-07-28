package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.Profile
import com.gotchai.domain.user.port.out.ProfileCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.entity.ProfileEntity
import com.gotchai.storage.rdb.user.repository.ProfileJpaRepository

@Adapter
class ProfileCommandAdapter(
    private val profileRepository: ProfileJpaRepository,
) : ProfileCommandPort {
    override fun createProfile(creation: Profile.Creation): Profile =
        profileRepository.save(ProfileEntity.from(creation))
            .toProfile()
}
