package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.quiz.dto.result.GetQuizResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.exception.QuizNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service

@Service
class QuizQueryService(
    private val quizQueryPort: QuizQueryPort,
    private val quizPickQueryPort: QuizPickQueryPort
) : QuizQueryUseCase {
    override fun getQuizById(id: Long): GetQuizResult {
        val quiz = quizQueryPort.getQuizById(id) ?: throw QuizNotFoundException()
        val quizPicks = quizPickQueryPort.getQuizPicksByQuizId(quiz.id)

        val (aiPick, humanPick) =
            AnswerType.entries.map { type ->
                quizPicks.filter { it.type == type }.random()
            }

        return GetQuizResult.of(quiz, listOf(aiPick, humanPick).shuffled())
    }
}
