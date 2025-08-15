package com.gotchai.api.presentation.v1.badge

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.badgeResponseFields
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.getMyBadgesResponseFields
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgesResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createBadge
import com.gotchai.domain.fixture.createGetMyBadgesResult
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(BadgeController::class)
class BadgeControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var badgeQueryUseCase: BadgeQueryUseCase

    init {
        describe("getBadgeById()는") {
            context("조회하려는 뱃지가 존재하는 경우") {
                every { badgeQueryUseCase.getBadgeById(ID) } returns createBadge()

                it("상태 코드 200과 BadgeResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/badges/{badgeId}", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<BadgeResponse>>()
                        .document("식별자 기반 뱃지 단일 조회 성공(200)") {
                            pathParams("badgeId" desc "뱃지 식별자")
                            responseBody(badgeResponseFields)
                        }
                }
            }

            context("조회하려는 뱃지가 존재하지 않는 경우") {
                every { badgeQueryUseCase.getBadgeById(any()) } throws BadgeNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/badges/{badgeId}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("식별자 기반 뱃지 단일 조회 실패(404)") {
                            pathParams("badgeId" desc "뱃지 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("getMyBadges()는") {
            every { badgeQueryUseCase.getMyBadges(ID) } returns createGetMyBadgesResult()

            it("상태 코드 200과 GetMyBadgeResponse들을 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/users/me/badges")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<GetMyBadgesResponse>>()
                    .document("내가 취득한 뱃지 리스트 조회 성공(200)") {
                        responseBody(getMyBadgesResponseFields)
                    }
            }
        }
    }
}
