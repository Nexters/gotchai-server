package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.common.util.generateNickname
import com.gotchai.domain.auth.dto.command.LoginCommand
import com.gotchai.domain.auth.dto.command.RefreshCommand
import com.gotchai.domain.auth.dto.command.SignUpCommand
import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.auth.dto.result.LoginResult
import com.gotchai.domain.auth.dto.result.RefreshResult
import com.gotchai.domain.auth.dto.result.SocialLoginResult
import com.gotchai.domain.auth.entity.RefreshToken
import com.gotchai.domain.auth.exception.*
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import com.gotchai.domain.auth.port.out.OAuthQueryPort
import com.gotchai.domain.auth.port.out.RefreshTokenCommandPort
import com.gotchai.domain.auth.port.out.RefreshTokenQueryPort
import com.gotchai.domain.global.provider.TokenProvider
import com.gotchai.domain.global.security.GotchaiAuthentication
import com.gotchai.domain.user.entity.Profile
import com.gotchai.domain.user.entity.Role
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.entity.UserSocial
import com.gotchai.domain.user.exception.UserNotFoundException
import com.gotchai.domain.user.port.out.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class AuthCommandService(
    private val userQueryPort: UserQueryPort,
    private val userCommandPort: UserCommandPort,
    private val profileQueryPort: ProfileQueryPort,
    private val profileCommandPort: ProfileCommandPort,
    private val userSocialQueryPort: UserSocialQueryPort,
    private val userSocialCommandPort: UserSocialCommandPort,
    private val refreshTokenQueryPort: RefreshTokenQueryPort,
    private val refreshTokenCommandPort: RefreshTokenCommandPort,
    private val oAuthQueryPort: OAuthQueryPort,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.refresh-token-expiration}")
    private val refreshTokenExpiration: Duration
) : AuthCommandUseCase {
    @Transactional
    override fun login(
        deviceId: String?,
        command: LoginCommand
    ): LoginResult =
        with(command) {
            val user = userQueryPort.getUserByEmail(command.email) ?: throw UserNotFoundException()

            if (user.password == null) throw UnsupportedLoginMethodException()
            if (!passwordEncoder.matches(password, user.password)) throw InvalidPasswordException()

            val authentication = GotchaiAuthentication.from(user)
            val (accessToken, refreshToken) = createAccessAndRefreshToken(deviceId, authentication)

            LoginResult(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }

    @Transactional
    override fun socialLogin(
        deviceId: String?,
        command: SocialLoginCommand
    ): SocialLoginResult =
        with(command) {
            val oAuthUser =
                runCatching { oAuthQueryPort.getOAuthUserByToken(oAuthToken, provider) }
                    .getOrElse { throw InvalidOAuthTokenException() }
            val userSocial = userSocialQueryPort.getUserSocialBySocialId(oAuthUser.id)

            val user =
                if (userSocial == null) {
                    signUp(
                        SignUpCommand(
                            email = oAuthUser.email,
                            password = null
                        )
                    ).apply {
                        userSocialCommandPort.createUserSocial(
                            UserSocial.Creation(
                                userId = id,
                                socialId = oAuthUser.id,
                                provider = provider
                            )
                        )
                    }
                } else {
                    userQueryPort.getUserById(userSocial.userId) ?: throw UserNotFoundException()
                }

            val authentication = GotchaiAuthentication.from(user)
            val (accessToken, refreshToken) = createAccessAndRefreshToken(deviceId, authentication)

            SocialLoginResult(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }

    @Transactional
    override fun signUp(command: SignUpCommand): User =
        with(command) {
            userQueryPort
                .getUserByEmail(email)
                ?.run { throw AccountAlreadyExistsException() }

            val profiles = profileQueryPort.getProfiles()
            val nickname = generateNickname(profiles.map { it.nickname })

            val user =
                userCommandPort.createUser(
                    User.Creation(
                        email = email,
                        password = password?.let { passwordEncoder.encode(it) },
                        roles = setOf(Role.MEMBER)
                    )
                )

            profileCommandPort.createProfile(
                Profile.Creation(
                    userId = user.id,
                    nickname = nickname
                )
            )

            return user
        }

    override fun refresh(
        deviceId: String?,
        command: RefreshCommand
    ): RefreshResult {
        val authentication = tokenProvider.getAuthentication(command.refreshToken)

        refreshTokenQueryPort
            .getRefreshTokenByUserId(authentication.userId)
            ?.run {
                if (content != command.refreshToken) {
                    refreshTokenCommandPort.deleteRefreshTokenByUserId(authentication.userId)

                    throw InvalidRefreshTokenException()
                }
            }
            ?: throw RefreshTokenNotFoundException()

        val (accessToken, refreshToken) = createAccessAndRefreshToken(deviceId, authentication)

        return RefreshResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun logout(userId: Long) {
        refreshTokenCommandPort.deleteRefreshTokenByUserId(userId)
    }

    @Transactional
    override fun withdrawal(userId: Long) {
        userCommandPort.deleteByUserId(userId)
        userSocialCommandPort.deleteByUserId(userId)
        profileCommandPort.deleteByUserId(userId)
        refreshTokenCommandPort.deleteRefreshTokenByUserId(userId)
    }

    private fun createAccessAndRefreshToken(
        deviceId: String?,
        authentication: GotchaiAuthentication
    ): Pair<String, String> {
        val accessToken = tokenProvider.createAccessToken(authentication)
        val refreshToken =
            tokenProvider
                .createRefreshToken(authentication)
                .also {
                    refreshTokenCommandPort.createRefreshToken(
                        RefreshToken.Creation(
                            userId = authentication.userId,
                            deviceId = deviceId,
                            content = it,
                            expiration = refreshTokenExpiration
                        )
                    )
                }

        return accessToken to refreshToken
    }
}
