package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.quiz.dto.result.QuizPickResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.QuizPickScore
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.domain.quiz.port.out.QuizScoreCommandPort
import com.gotchai.domain.quiz.port.out.QuizScoreQueryPort
import org.springframework.stereotype.Service

@Service
class QuizCommandService(
    private val quizPickQueryPort: QuizPickQueryPort,
    private val quizScoreCommandPort: QuizScoreCommandPort,
    private val quizScoreQueryPort: QuizScoreQueryPort,
) : QuizCommandUseCase {
    override fun scoreQuiz(examId: Long, quizPickId: Long, userId: Long): QuizPickResult {
        val quizPick = quizPickQueryPort.getQuizPickBy(quizPickId)
        val isAnswer = quizPick.type == AnswerType.AI

        val quizPickScoreId = "exam:$examId:$userId"
        val quizPickScore = QuizPickScore(quizPick.id, isAnswer)

        updateQuizScore(quizPickScoreId, quizPickScore, examId)

        return QuizPickResult.of(quizPick.contents, isAnswer)
    }

    private fun updateQuizScore(quizScoreId: String, quizPickScore: QuizPickScore, examId: Long) {
        val quizScore = quizScoreQueryPort.getScoreById(quizScoreId)
        val updatedScores = (quizScore?.scores ?: emptyList()) + quizPickScore

        if (quizScore == null) {
            quizScoreCommandPort.create(quizScoreId, updatedScores, examId)
        } else {
            quizScoreCommandPort.updateScore(quizScoreId, updatedScores)
        }
    }
}
