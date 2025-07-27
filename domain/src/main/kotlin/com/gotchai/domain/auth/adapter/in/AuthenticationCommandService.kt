package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.common.util.user.UserNameGenerator
import com.gotchai.domain.auth.dto.CredentialSocial
import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.entity.TokenStatus
import com.gotchai.domain.auth.exception.UnmatchedUserPasswordException
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
import com.gotchai.domain.auth.port.out.AuthenticationHistoryCommandPort
import com.gotchai.domain.auth.port.out.TokenCommandPort
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.SocialType
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.exception.UserNotFoundException
import com.gotchai.domain.user.port.`in`.UserCommandUseCase
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationCommandService(
    private val userQueryUseCase: UserQueryUseCase,
    private val userCommandUseCase: UserCommandUseCase,
    private val authenticationHistoryCommandPort: AuthenticationHistoryCommandPort,
    private val tokenCommandPort: TokenCommandPort,
    private val userNameGenerator: UserNameGenerator,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationCommandUseCase {
    override fun testLogin(
        email: String,
        password: String,
    ): TokenPair {
        val user =
            userQueryUseCase.getUserCredentialByEmail(email)
                ?: throw UserNotFoundException()

        if (!passwordEncoder.matches(password, user.password)) {
            throw UnmatchedUserPasswordException()
        }

        return tokenCommandPort.create(null, User.Issue(user.id)).apply {
            authenticationHistoryCommandPort.create(
                AuthenticationHistory.Creation(
                    userId = user.id,
                    deviceId = null,
                    tokenPair =
                        TokenPair(
                            accessToken = this.accessToken,
                            refreshToken = this.refreshToken,
                        ),
                    status = TokenStatus.ACTIVE,
                ),
            )
        }
    }

    override fun testSignUp(
        name: String,
        email: String,
        password: String,
    ) {
        userCommandUseCase.testSignUp(name, email, passwordEncoder.encode(password))
    }

    override fun socialLogin(
        deviceId: String?,
        email: String,
        socialId: String,
        socialType: SocialType,
    ): TokenPair {
        val existingUser = userQueryUseCase.getUserCredentialBySocial(socialId)

        val socialUser =
            if (existingUser != null) {
                SocialUser(
                    id = existingUser.id,
                    name = existingUser.name,
                    socialId = existingUser.socialId,
                    socialType = existingUser.socialType,
                )
            } else {
                val users = userQueryUseCase.getAll()
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

        val tokenPair =
            tokenCommandPort.create(deviceId, socialUser).apply {
                authenticationHistoryCommandPort.create(
                    AuthenticationHistory.Creation(
                        userId = socialUser.id,
                        deviceId = deviceId,
                        tokenPair =
                            TokenPair(
                                accessToken = this.accessToken,
                                refreshToken = this.refreshToken,
                            ),
                        status = TokenStatus.ACTIVE,
                    ),
                )
            }

        return tokenPair
    }

    override fun renew(refreshToken: String) = tokenCommandPort.refresh(refreshToken)

    override fun logout(token: String) = tokenCommandPort.remove(token)

    override fun withdrawUser(userId: Long) {
        tokenCommandPort.removeByUserId(userId)
    }

    private fun createNewSocialUser(credentialSocial: CredentialSocial): SocialUser {
        val newUser =
            userCommandUseCase.create(
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
