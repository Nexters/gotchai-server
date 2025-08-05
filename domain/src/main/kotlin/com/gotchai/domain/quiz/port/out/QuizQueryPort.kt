package com.gotchai.domain.quiz.port.out

import com.gotchai.domain.quiz.entity.Quiz

interface QuizQueryPort {
    fun getQuizBy(quizId: Long): Quiz?

    fun getQuizzesByExamId(examId: Long): List<Quiz>
}
