package com.gotchai.storage.rdb.user.adapter.out

import com.gotchai.domain.user.entity.Profile
import com.gotchai.domain.user.port.out.ProfileCommandPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.user.entity.ProfileEntity
import com.gotchai.storage.rdb.user.repository.ProfileJpaRepository

@Adapter
class ProfileCommandAdapter(
    private val profileJpaRepository: ProfileJpaRepository
) : ProfileCommandPort {
    override fun createProfile(creation: Profile.Creation): Profile =
        profileJpaRepository
            .save(ProfileEntity.from(creation))
            .toProfile()

    override fun deleteByUserId(userId: Long) {
        profileJpaRepository
            .findByUserId(userId)
            ?.softDelete()
    }
}
