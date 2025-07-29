package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.user.entity.Profile
import com.gotchai.domain.user.exception.ProfileNotFoundException
import com.gotchai.domain.user.port.`in`.ProfileQueryUseCase
import com.gotchai.domain.user.port.out.ProfileQueryPort
import org.springframework.stereotype.Service

@Service
class ProfileQueryService(
    private val profileQueryPort: ProfileQueryPort,
) : ProfileQueryUseCase {
    override fun getProfileByUserId(userId: Long): Profile? =
        profileQueryPort.getProfileByUserId(userId) ?: throw ProfileNotFoundException()
}
