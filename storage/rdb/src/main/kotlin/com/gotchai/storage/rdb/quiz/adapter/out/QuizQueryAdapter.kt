package com.gotchai.storage.rdb.quiz.adapter.out

import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import com.gotchai.storage.rdb.quiz.repository.QuizJpaRepository
import org.springframework.stereotype.Repository

@Repository
class QuizQueryAdapter(
    private val quizJpaRepository: QuizJpaRepository
) : QuizQueryPort {
    @ReadOnlyTransactional
    override fun getQuizBy(quizId: Long): Quiz = quizJpaRepository.findByIdOrElseThrow(quizId).toQuiz()

    @ReadOnlyTransactional
    override fun getQuizzesByExamId(examId: Long): List<Quiz> =
        quizJpaRepository
            .findAllByExamIdOrderByOrderAsc(examId)
            .map { it.toQuiz() }
}
