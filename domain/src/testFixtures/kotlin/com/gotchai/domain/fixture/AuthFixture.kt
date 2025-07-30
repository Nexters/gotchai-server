package com.gotchai.domain.fixture

import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.auth.dto.command.LoginCommand
import com.gotchai.domain.auth.dto.command.RefreshCommand
import com.gotchai.domain.auth.dto.command.SignUpCommand
import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.auth.dto.result.LoginResult
import com.gotchai.domain.auth.dto.result.RefreshResult
import com.gotchai.domain.auth.dto.result.SocialLoginResult
import com.gotchai.domain.auth.entity.RefreshToken
import com.gotchai.domain.user.entity.SocialProvider
import java.time.Duration

const val DEVICE_ID = "device-1"
const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
val EXPIRATION = Duration.ofHours(1)!!

fun createRefreshToken(
    userId: Long = ID,
    deviceId: String? = DEVICE_ID,
    content: String = TOKEN,
    expiration: Duration = EXPIRATION
): RefreshToken =
    RefreshToken(
        userId = userId,
        deviceId = deviceId,
        content = content,
        expiration = expiration
    )

fun createOAuthUser(
    id: String = SOCIAL_ID,
    email: String = EMAIL,
    provider: SocialProvider = PROVIDER
): OAuthUser =
    OAuthUser(
        id = id,
        email = email,
        provider = provider
    )

fun createLoginCommand(
    email: String = EMAIL,
    password: String = PASSWORD
): LoginCommand =
    LoginCommand(
        email = email,
        password = password
    )

fun createRefreshCommand(refreshToken: String = TOKEN): RefreshCommand =
    RefreshCommand(
        refreshToken = refreshToken
    )

fun createSignUpCommand(
    email: String = EMAIL,
    password: String? = PASSWORD
): SignUpCommand =
    SignUpCommand(
        email = email,
        password = password
    )

fun createSocialLoginCommand(
    oAuthToken: String = TOKEN,
    provider: SocialProvider = PROVIDER
): SocialLoginCommand =
    SocialLoginCommand(
        oAuthToken = oAuthToken,
        provider = provider
    )

fun createLoginResult(
    accessToken: String = TOKEN,
    refreshToken: String = TOKEN
): LoginResult =
    LoginResult(
        accessToken = accessToken,
        refreshToken = refreshToken
    )

fun createRefreshResult(
    accessToken: String = TOKEN,
    refreshToken: String = TOKEN
): RefreshResult =
    RefreshResult(
        accessToken = accessToken,
        refreshToken = refreshToken
    )

fun createSocialLoginResult(
    accessToken: String = TOKEN,
    refreshToken: String = TOKEN
): SocialLoginResult =
    SocialLoginResult(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
