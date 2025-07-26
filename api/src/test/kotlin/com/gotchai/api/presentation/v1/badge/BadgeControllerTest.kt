package com.gotchai.api.presentation.v1.badge

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.badgeResponseFields
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.getMyBadgeResponseFields
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import com.gotchai.api.presentation.v1.badge.dto.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.dto.response.GetMyBadgeResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.paramDesc
import com.gotchai.api.util.toListFields
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.`in`.BadgeCommandUseCase
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createBadge
import com.gotchai.domain.fixture.createGetMyBadgeResult
import com.gotchai.domain.fixture.createUser
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(BadgeController::class)
class BadgeControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var badgeQueryUseCase: BadgeQueryUseCase

    @MockkBean
    private lateinit var badgeCommandUseCase: BadgeCommandUseCase

    init {
        describe("getBadgeById()는") {
            context("조회하려는 뱃지가 존재하는 경우") {
                val badge =
                    createBadge()
                        .also {
                            every { badgeQueryUseCase.getBadgeById(it.id) } returns it
                        }

                it("상태 코드 200과 BadgeResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/badges/{id}", badge.id)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<BadgeResponse>>()
                        .document("식별자 기반 뱃지 단일 조회 성공(200)") {
                            pathParams("id" paramDesc "식별자")
                            responseBody(badgeResponseFields)
                        }
                }
            }

            context("조회하려는 뱃지가 존재하지 않는 경우") {
                every { badgeQueryUseCase.getBadgeById(any()) } throws BadgeNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/badges/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectBody<ApiResponse<ErrorResponse>>()
                        .document("식별자 기반 뱃지 단일 조회 실패(404)") {
                            pathParams("id" paramDesc "식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("getMyBadges()는") {
            context("뱃지를 취득한 사용자가 존재하는 경우") {
                val user = createUser()
                val results = listOf(createGetMyBadgeResult())

                every { badgeQueryUseCase.getMyBadges(user.id) } returns results

                it("상태 코드 200과 GetMyBadgeResponse들을 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/users/me/badges")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<List<GetMyBadgeResponse>>>()
                        .document("내가 취득한 뱃지 리스트 조회 성공(200)") {
                            responseBody(getMyBadgeResponseFields.toListFields())
                        }
                }
            }
        }
    }
}
