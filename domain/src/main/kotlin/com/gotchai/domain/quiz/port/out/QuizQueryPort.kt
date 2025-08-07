package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.Quiz

interface QuizQueryPort {
    fun getQuizById(id: Long): Quiz?

    fun getQuizzesByExamId(examId: Long): List<Quiz>
}
