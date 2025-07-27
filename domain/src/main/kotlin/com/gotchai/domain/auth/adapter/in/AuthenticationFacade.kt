package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.common.util.user.UserNameGenerator
import com.gotchai.domain.auth.dto.CredentialSocial
import com.gotchai.domain.auth.dto.Token
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.SocialType
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.out.UserCommandPort
import com.gotchai.domain.user.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class AuthenticationFacade(
    private val authenticationCommandUseCase: AuthenticationCommandUseCase,
    private val userQueryPort: UserQueryPort,
    private val userCommandPort: UserCommandPort,
    private val userNameGenerator: UserNameGenerator,
) {
    fun socialLogin(
        deviceId: String?,
        email: String,
        socialId: String,
        socialType: SocialType,
    ): Token {
        val existingUser = userQueryPort.findBySocialId(socialId)

        val socialUser =
            if (existingUser != null) {
                SocialUser(
                    id = existingUser.id,
                    name = existingUser.name,
                    socialId = existingUser.socialId!!,
                    socialType = existingUser.socialType,
                )
            } else {
                val users = userQueryPort.findAll()
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
            authenticationCommandUseCase.socialLogin(
                deviceId = deviceId,
                socialUser = socialUser,
            )

        return token
    }

    private fun createNewSocialUser(credentialSocial: CredentialSocial): SocialUser {
        val newUser =
            userCommandPort.save(
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
