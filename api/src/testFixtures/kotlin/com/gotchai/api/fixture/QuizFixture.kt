package com.gotchai.api.fixture

import com.gotchai.api.presentation.v1.quiz.request.GradeQuizRequest
import com.gotchai.domain.fixture.ID

fun createGradeQuizRequest(quizPickId: Long = ID): GradeQuizRequest = GradeQuizRequest(quizPickId = quizPickId)
