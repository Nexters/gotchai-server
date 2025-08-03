package com.gotchai.storage.redis.exam.entity

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.quiz.entity.QuizHistory
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.Duration
import java.time.LocalDateTime

@RedisHash
class ExamHistoryEntity(
    @Id
    val historyId: String, // ex) exam:$examId:$userId
    var histories: List<QuizHistory>,
    val examId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val expiration: Duration
) {
    @TimeToLive
    private var ttl: Long = expiration.toSeconds()

    companion object {
        fun from(creation: ExamHistory.Creation): ExamHistoryEntity =
            with(creation) {
                ExamHistoryEntity(
                    historyId = historyId,
                    histories = histories,
                    examId = examId,
                    createdAt = createdAt,
                    expiration = expiration
                )
            }
    }

    fun toExamHistory(): ExamHistory =
        ExamHistory(
            historyId = historyId,
            histories = histories,
            examId = examId,
            createdAt = createdAt,
            expiration = expiration
        )

    fun updateHistories(histories: List<QuizHistory>) {
        this.histories = histories
    }
}
