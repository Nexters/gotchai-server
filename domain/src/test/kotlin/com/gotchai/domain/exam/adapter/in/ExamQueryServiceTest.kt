package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.fixture.createExam
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ExamQueryServiceTest : BehaviorSpec() {
    private val examQueryPort = mockk<ExamQueryPort>()
    private val examQueryService = ExamQueryService(examQueryPort)

    init {
        Given("시험이 존재하는 경우") {
            val exam =
                createExam()
                    .also {
                        every { examQueryPort.getExamById(it.id) } returns it
                    }

            When("해당 시험을 조회하면") {
                val result = examQueryService.getExamById(exam.id)

                Then("해당 시험이 반환된다.") {
                    result shouldBe exam
                }
            }
        }

        Given("여러 시험이 존재하는 경우") {
            val exams =
                listOf(
                    createExam(id = 1L, title = "AI와 크리스마스 파티"),
                    createExam(id = 2L, title = "Gotchai 팀 화이팅!"),
                )
            every { examQueryPort.getExams() } returns exams

            When("모든 시험을 조회하면") {
                val result = examQueryService.getExams()

                Then("모든 시험이 반환된다.") {
                    result shouldBe exams
                }
            }
        }

        Given("시험이 존재하지 않는 경우") {
            every { examQueryPort.getExams() } returns emptyList()

            When("모든 시험을 조회하면") {
                val result = examQueryService.getExams()

                Then("빈 리스트가 반환된다.") {
                    result shouldBe emptyList()
                }
            }
        }
    }
}
