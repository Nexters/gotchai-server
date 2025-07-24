package com.gotchai.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userValidator: UserValidator,
) {
    fun create(create: User.SocialCreation): User.Info = userAppender.append(create)

    fun getAll(): List<User.Profile> = userReader.readAll()

    fun getUserIssue(userId: Long): User.Issue = userReader.readUserIssue(userId)

    fun getUserCredentialByEmail(email: String): User.Credential? = userReader.readCredentialByEmail(email)

    fun getUserCredentialBySocial(socialId: String): SocialUser? {
        val userInfo = userReader.readBySocialId(socialId)
        return userInfo?.let {
            SocialUser(
                id = userInfo.id,
                name = userInfo.name,
                socialId = userInfo.socialId!!,
                socialType = userInfo.socialType,
            )
        }
    }

    fun testSignUp(
        name: String,
        email: String,
        password: String,
    ) {
        userValidator.verifyEmail(email)
        userAppender.append(
            User.GotchaiCreation(
                name = name,
                email = email,
                password = password,
            ),
        )
    }
}
