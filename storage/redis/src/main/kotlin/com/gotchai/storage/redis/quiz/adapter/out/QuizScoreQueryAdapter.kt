package com.gotchai.storage.redis.quiz.adapter.out

import com.gotchai.domain.quiz.entity.QuizScore
import com.gotchai.domain.quiz.port.out.QuizScoreQueryPort
import com.gotchai.storage.redis.global.annotation.Adapter
import com.gotchai.storage.redis.quiz.repository.QuizScoreRedisRepository

@Adapter
class QuizScoreQueryAdapter(
    private val quizScoreRedisRepository: QuizScoreRedisRepository
): QuizScoreQueryPort {
    override fun getScoreById(quizScoreId: String): QuizScore? =
        quizScoreRedisRepository.findByQuizScoreId(quizScoreId)?.toQuizScore()
}
