package com.gotchai.api.fixture

import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse

const val PARTICIPANT_COUNT = 1

fun createExamGetParticipantCountResponse(participantCount: Int = PARTICIPANT_COUNT): GetExamParticipantCountResponse =
    GetExamParticipantCountResponse(participantCount = participantCount)
