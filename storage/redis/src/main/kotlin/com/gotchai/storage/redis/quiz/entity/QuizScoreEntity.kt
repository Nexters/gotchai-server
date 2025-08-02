package com.gotchai.storage.redis.quiz.entity

import com.gotchai.domain.quiz.entity.QuizPickScore
import com.gotchai.domain.quiz.entity.QuizScore
import java.time.Duration
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class QuizScoreEntity (
    @Id
    val quizScoreId: String,
    val scores: List<QuizPickScore>,
    val startQuizId: Long? = null,
    val createdAt: LocalDateTime,
    val expiration: Duration
) {
    @TimeToLive
    private val ttl = expiration.toSeconds()

    companion object {
        fun from(creation: QuizScore.Creation): QuizScoreEntity =
            with(creation) {
                QuizScoreEntity(
                    quizScoreId = quizScoreId,
                    scores = scores,
                    startQuizId = startQuizId,
                    createdAt = createdAt,
                    expiration = expiration,
                )
            }
    }

    fun toQuizScore(): QuizScore =
        QuizScore(
            quizScoreId = quizScoreId,
            scores = scores,
            startQuizId = startQuizId,
            createdAt = createdAt,
            expiration = expiration,
        )
}
