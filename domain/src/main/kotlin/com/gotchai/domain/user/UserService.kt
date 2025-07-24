package com.gotchai.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userValidator: UserValidator,
) {
    fun getUserIssue(userId: Long): User.Issue = userReader.readUserIssue(userId)

    fun getUserCredentialByEmail(email: String): User.Credential = userReader.readCredentialByEmail(email)

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
