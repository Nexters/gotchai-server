package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeQueryPort
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createBadge
import com.gotchai.domain.fixture.createGetMyBadgeResult
import com.gotchai.domain.fixture.createUser
import com.gotchai.domain.fixture.createUserBadge
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class BadgeQueryServiceTest : BehaviorSpec() {
    private val badgeQueryPort = mockk<BadgeQueryPort>()
    private val userBadgeQueryPort = mockk<UserBadgeQueryPort>()
    private val badgeQueryService =
        BadgeQueryService(
            badgeQueryPort = badgeQueryPort,
            userBadgeQueryPort = userBadgeQueryPort,
        )

    init {
        Given("임의의 뱃지가 존재하는 경우") {
            val badge =
                createBadge()
                    .also {
                        every { badgeQueryPort.getBadgeById(it.id) } returns it
                    }

            When("유저가 해당 뱃지를 조회하면") {
                val result = badgeQueryService.getBadgeById(badge.id)

                Then("해당 뱃지가 반환된다.") {
                    result shouldBe badge
                }
            }
        }

        Given("어떠한 뱃지도 존재하지 않는 경우") {
            every { badgeQueryPort.getBadgeById(any()) } throws BadgeNotFoundException()

            When("특정 뱃지를 조회하면") {
                Then("예외가 발생한다.") {
                    shouldThrow<BadgeNotFoundException> { badgeQueryService.getBadgeById(ID) }
                }
            }
        }

        Given("뱃지를 취득한 유저가 있는 경우") {
            val user = createUser()
            val userBadges = listOf(createUserBadge())
            val badges = listOf(createBadge())

            every { userBadgeQueryPort.getUserBadgesByUserId(user.id) } returns userBadges
            every { badgeQueryPort.getBadgesByIdIn(userBadges.map { it.badgeId }) } returns badges

            When("해당 유저가 본인의 뱃지를 조회하면") {
                val result = badgeQueryService.getMyBadges(user.id)

                Then("본인이 취득한 뱃지들이 반환된다.") {
                    result shouldBe listOf(createGetMyBadgeResult())
                }
            }
        }
    }
}
