package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.exam.port.out.GeminiModelPort
import com.gotchai.domain.quiz.dto.result.GetQuizResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.exception.QuizNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuizQueryService(
    private val quizQueryPort: QuizQueryPort,
    private val quizPickQueryPort: QuizPickQueryPort,
    private val geminiModelPort: GeminiModelPort,
    private val examQueryPort: ExamQueryPort
) : QuizQueryUseCase {
    @Transactional(readOnly = true)
    override fun getQuizById(quizId: Long): GetQuizResult {
        val quiz = quizQueryPort.getQuizById(quizId) ?: throw QuizNotFoundException()
        val quizPicks = quizPickQueryPort.getQuizPicksByQuizId(quiz.id)
        val exam = examQueryPort.getExamById(quiz.examId) ?: throw ExamNotFoundException()
        val geminiMessage = geminiModelPort.callGemini(exam.prompt, quiz.contents)

        val (aiPick, humanPick) =
            AnswerType.entries.map { type ->
                quizPicks.filter { it.type == type }.random()
            }

        return GetQuizResult.of(quiz, listOf(aiPick.copy(contents = geminiMessage.message ?: aiPick.contents), humanPick).shuffled())
    }
}
