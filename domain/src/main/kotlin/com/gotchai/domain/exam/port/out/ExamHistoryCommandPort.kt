package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.quiz.entity.QuizHistory

interface ExamHistoryCommandPort {
    fun create(creation: ExamHistory.Creation)

    fun updateHistory(
        historyId: String,
        histories: List<QuizHistory>
    )
}
