package com.gotchai.domain.auth

import com.gotchai.common.enum.user.SocialType
import com.gotchai.domain.user.SocialUser
import com.gotchai.domain.user.User
import com.gotchai.domain.user.UserNameGenerator
import com.gotchai.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class AuthenticationFacade(
    private val authenticationService: AuthenticationService,
    private val userNameGenerator: UserNameGenerator,
    private val userService: UserService,
) {
    fun socialLogin(
        deviceId: String?,
        email: String,
        socialId: String,
        socialType: SocialType,
    ): Token {
        val existingUser = userService.getUserCredentialBySocial(socialId)

        val socialUser =
            if (existingUser != null) {
                existingUser
            } else {
                val users = userService.getAll()
                val names = users.map { it.name }
                createNewSocialUser(
                    CredentialSocial(
                        name = userNameGenerator.generateName(names),
                        email = email,
                        socialId = socialId,
                        socialType = socialType,
                    ),
                )
            }

        val token =
            authenticationService.socialLogin(
                deviceId = deviceId,
                socialUser = socialUser,
            )

        return token
    }

    fun createNewSocialUser(credentialSocial: CredentialSocial): SocialUser {
        val newUser =
            userService.create(
                User.SocialCreation(
                    name = credentialSocial.name,
                    email = credentialSocial.email,
                    socialId = credentialSocial.socialId,
                    socialType = credentialSocial.socialType,
                ),
            )

        val socialUser =
            SocialUser(
                id = newUser.id,
                name = credentialSocial.name,
                socialId = credentialSocial.socialId,
                socialType = credentialSocial.socialType,
            )

        return socialUser
    }
}
