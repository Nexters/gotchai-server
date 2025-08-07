package com.gotchai.storage.rdb.quiz.adapter.out

import com.gotchai.domain.quiz.entity.QuizPick
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.gotchai.storage.rdb.quiz.repository.QuizPickJpaRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class QuizPickQueryAdapter(
    private val quizPickJpaRepository: QuizPickJpaRepository
) : QuizPickQueryPort {
    @ReadOnlyTransactional
    override fun getQuizPickById(id: Long): QuizPick? = quizPickJpaRepository.findByIdOrNull(id)?.toQuizPick()

    @ReadOnlyTransactional
    override fun getQuizPicksByQuizId(quizId: Long): List<QuizPick> = quizPickJpaRepository.findAllByQuizId(quizId).map { it.toQuizPick() }
}
