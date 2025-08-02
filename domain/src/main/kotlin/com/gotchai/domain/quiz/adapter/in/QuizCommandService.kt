package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.QuizCommandPort
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service

@Service
class QuizCommandService(
    private val quizQueryPort: QuizQueryPort,
    private val quizCommandPort: QuizCommandPort,
    private val quizPickQueryPort: QuizPickQueryPort,
    private val quizPickCommandPort: QuizCommandPort,
): QuizCommandUseCase {
    override fun scoreQuiz(quizId: Long, userId: Long) {

    }
}
