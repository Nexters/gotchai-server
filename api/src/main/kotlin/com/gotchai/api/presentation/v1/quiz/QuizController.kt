package com.gotchai.api.presentation.v1.quiz

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.quiz.request.ScoreQuizRequest
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.presentation.v1.quiz.response.ScoreQuizResponse
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class QuizController(
    private val quizQueryUseCase: QuizQueryUseCase,
    private val quizCommandUseCase: QuizCommandUseCase
) {
    @GetMapping("/quizzes/{id}")
    fun getQuizById(
        @PathVariable(name = "id") quizId: Long,
        @AuthenticationPrincipal
        userId: Long
    ): QuizDetailResponse = QuizDetailResponse.from(quizQueryUseCase.getQuizById(quizId))

    @PostMapping("/quizzes/score")
    fun scoreQuiz(
        @RequestBody request: ScoreQuizRequest,
        @AuthenticationPrincipal
        userId: Long
    ): ScoreQuizResponse = ScoreQuizResponse.from(
        quizCommandUseCase.scoreQuiz(
            request.examId,
            request.quizPickId,
            userId
        )
    )
}
