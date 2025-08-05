package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.port.`in`.BadgeCommandUseCase
import com.gotchai.domain.badge.port.out.UserBadgeCommandPort
import org.springframework.stereotype.Service

@Service
class BadgeCommandService(
    private val userBadgeCommandPort: UserBadgeCommandPort
) : BadgeCommandUseCase {
    override fun createUserBadge(creation: UserBadge.Creation) {
        userBadgeCommandPort.createUserBadge(creation)
    }
}
