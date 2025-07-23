package com.gotchai.domain.auth

import com.gotchai.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class AuthenticationFacade(
    private val userService: UserService,
    private val authenticationService: AuthenticationService,
)
