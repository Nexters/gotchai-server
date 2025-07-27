package com.gotchai.domain.user.port.`in`

import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User

interface UserQueryUseCase {
    fun getAll(): List<User.Profile>

    fun getUserIssue(userId: Long): User.Issue

    fun getUserCredentialByEmail(email: String): User.Credential?

    fun getUserCredentialBySocial(socialId: String): SocialUser?
}
