package com.gotchai.api.util

import com.gotchai.domain.fixture.createUser
import com.gotchai.domain.global.security.GotchaiAuthentication
import com.gotchai.domain.user.entity.User
import org.springframework.security.core.context.SecurityContextHolder

fun withMockUser(user: User? = null): User =
    (user ?: createUser())
        .also { SecurityContextHolder.getContext().authentication = GotchaiAuthentication.from(it) }
