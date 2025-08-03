package com.gotchai.storage.redis.quiz.entity

import com.gotchai.domain.quiz.entity.QuizPickScore
import com.gotchai.domain.quiz.entity.QuizScore
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.Duration
import java.time.LocalDateTime

@RedisHash
class QuizScoreEntity(
    @Id
    val quizScoreId: String, // ex) exam:$examId:$userId
    var scores: List<QuizPickScore>,
    val examId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val expiration: Duration
) {
    @TimeToLive
    private var ttl: Long = expiration.toSeconds()

    companion object {
        fun from(creation: QuizScore.Creation): QuizScoreEntity =
            with(creation) {
                QuizScoreEntity(
                    quizScoreId = quizScoreId,
                    scores = scores,
                    examId = examId,
                    createdAt = createdAt,
                    expiration = expiration
                )
            }
    }

    fun toQuizScore(): QuizScore =
        QuizScore(
            quizScoreId = quizScoreId,
            scores = scores,
            examId = examId,
            createdAt = createdAt,
            expiration = expiration
        )

    fun updateScores(scores: List<QuizPickScore>) {
        this.scores = scores
    }
}
