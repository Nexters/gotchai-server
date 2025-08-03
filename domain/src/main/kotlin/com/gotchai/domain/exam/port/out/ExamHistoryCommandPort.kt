package com.gotchai.domain.exam.port.out

import com.gotchai.domain.quiz.entity.QuizHistory
import java.time.Duration
import java.time.LocalDateTime

interface ExamHistoryCommandPort {
    fun create(
        historyId: String,
        histories: List<QuizHistory>,
        examId: Long,
        createdAt: LocalDateTime,
        expiration: Duration
    )

    fun updateHistory(
        historyId: String,
        histories: List<QuizHistory>
    )
}
