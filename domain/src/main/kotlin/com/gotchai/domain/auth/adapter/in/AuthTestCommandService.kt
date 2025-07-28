package com.gotchai.domain.auth.adapter.`in`

//class AuthTestCommandService : AuthTestCommandUseCase {
//    override fun testLogin(
//        email: String,
//        password: String,
//    ): TokenPair {
//        val user =
//            userQueryUseCase.getUserCredentialByEmail(email)
//                ?: throw UserNotFoundException()
//
//        if (!passwordEncoder.matches(password, user.password)) {
//            throw UnmatchedUserPasswordException()
//        }
//
//        return refreshTokenCommandPort.createRefreshToken(null, User.Issue(user.id)).apply {
//            authenticationHistoryCommandPort.create(
//                AuthenticationHistory.Creation(
//                    userId = user.id,
//                    deviceId = null,
//                    tokenPair =
//                        TokenPair(
//                            accessToken = this.accessToken,
//                            refreshToken = this.refreshToken,
//                        ),
//                    status = TokenStatus.ACTIVE,
//                ),
//            )
//        }
//    }
//
//    override fun testSignUp(
//        name: String,
//        email: String,
//        password: String,
//    ) {
//        userCommandUseCase.testSignUp(name, email, passwordEncoder.encode(password))
//    }
//}
