package com.gotchai.storage.redis.quiz.adapter.out

import com.gotchai.domain.quiz.entity.QuizPickScore
import com.gotchai.domain.quiz.entity.QuizScore
import com.gotchai.domain.quiz.port.out.QuizScoreCommandPort
import com.gotchai.storage.redis.global.annotation.Adapter
import com.gotchai.storage.redis.quiz.entity.QuizScoreEntity
import com.gotchai.storage.redis.quiz.repository.QuizScoreRedisRepository
import java.time.Duration
import java.time.LocalDateTime

@Adapter
class QuizScoreCommandAdapter(
    private val quizScoreRedisRepository: QuizScoreRedisRepository
) : QuizScoreCommandPort {
    override fun create(
        quizScoreId: String,
        scores: List<QuizPickScore>,
        examId: Long
    ) {
        quizScoreRedisRepository.save(
            QuizScoreEntity.from(
                QuizScore.Creation(
                    quizScoreId = quizScoreId,
                    scores = scores,
                    examId = examId,
                    createdAt = LocalDateTime.now(),
                    expiration = Duration.ofDays(1)
                )
            )
        )
    }

    override fun updateScore(
        quizScoreId: String,
        scores: List<QuizPickScore>
    ) {
        quizScoreRedisRepository.findById(quizScoreId).ifPresent {
            it.updateScores(scores)
            quizScoreRedisRepository.save(it)
        }
    }
}
