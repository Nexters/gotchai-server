package com.gotchai.api.presentation.v1.quiz

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class QuizController(
    private val quizQueryUseCase: QuizQueryUseCase
) {
    @GetMapping("/quizzes/{id}")
    fun getQuizById(
        @PathVariable(name = "id") quizId: Long,
        @AuthenticationPrincipal
        userId: Long
    ): QuizDetailResponse = QuizDetailResponse.from(quizQueryUseCase.getQuizById(quizId))
}
