package com.gotchai.api.presentation.v1.user

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.userRankingResponseFields
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.user.response.UserRankingResponse
import com.gotchai.api.util.document
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createGetUserRankingResult
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(UserController::class)
class UserControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var userQueryUseCase: UserQueryUseCase

    init {
        describe("getUserRanking()는") {
            every { userQueryUseCase.getUserRanking(ID) } returns createGetUserRankingResult()

            it("상태 코드 200과 UserRankingResponse를 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/users/me/ranking")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<UserRankingResponse>>()
                    .document("사용자 랭킹 조회 성공(200)") {
                        responseBody(userRankingResponseFields)
                    }
            }
        }
    }
}
