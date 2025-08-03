package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse

data class SubmitExamResponse(
    val title: String,
    val subTitle: String,
    val theme: String,
    val iconImage: String,
    val prompt: String,
    val answerCount: Long,
    val badge: BadgeResponse
)
