package com.gotchai.api.presentation.v1.auth

//class AuthTestController(
//    private val authCommandUseCase: AuthCommandUseCase,
//    private val oAuthQueryUseCase: OAuthQueryUseCase,
//) {
//    @PostMapping("/auth/test-login")
//    fun testLogin(
//        @RequestBody
//        request: LoginRequest,
//    ): AuthResponse.Token =
//        AuthResponse.Token.of(
//            authCommandUseCase.testLogin(
//                request.email,
//                request.password,
//            ),
//        )
//
//    @PostMapping("/auth/test-signup")
//    fun testSignUp(
//        @RequestBody
//        request: SignUpRequest,
//    ): AuthResponse.Message {
//        authCommandUseCase.testSignUp(
//            name = request.name,
//            email = request.email,
//            password = request.password,
//        )
//        return AuthResponse.Message("회원가입이 완료되었습니다.")
//    }
//
//}
