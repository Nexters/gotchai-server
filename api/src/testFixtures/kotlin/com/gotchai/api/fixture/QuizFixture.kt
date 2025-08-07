package com.gotchai.api.fixture

import com.gotchai.api.presentation.v1.quiz.request.GradeQuizRequest
import com.gotchai.domain.fixture.ID

fun createGradeQuizRequest(
    examId: Long = ID,
    quizPickId: Long = ID
): GradeQuizRequest =
    GradeQuizRequest(
        examId = examId,
        quizPickId = quizPickId
    )
