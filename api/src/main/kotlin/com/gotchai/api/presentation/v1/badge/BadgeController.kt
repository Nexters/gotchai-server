package com.gotchai.api.presentation.v1.badge

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgeResponse
import com.gotchai.domain.badge.port.`in`.BadgeCommandUseCase
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.global.security.GotchaiAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class BadgeController(
    private val badgeQueryUseCase: BadgeQueryUseCase,
    private val badgeCommandUseCase: BadgeCommandUseCase,
) {
    @GetMapping("/badges/{id}")
    fun getBadgeById(
        @PathVariable
        id: Long,
    ): BadgeResponse =
        badgeQueryUseCase
            .getBadgeById(id)
            .let { BadgeResponse.from(it) }

    @GetMapping("/users/me/badges")
    fun getMyBadges(
        @AuthenticationPrincipal
        authentication: GotchaiAuthentication,
    ): List<GetMyBadgeResponse> =
        badgeQueryUseCase
            .getMyBadges(authentication.userId)
            .map { GetMyBadgeResponse.from(it) }
}
