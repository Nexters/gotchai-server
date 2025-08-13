package com.gotchai.domain.fixture

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.exam.dto.result.ExamResult
import com.gotchai.domain.exam.dto.result.StartExamResult
import com.gotchai.domain.exam.dto.result.SubmitExamResult
import com.gotchai.domain.exam.entity.Exam
import java.time.LocalDateTime

const val TITLE = "AI와 크리스마스 파티"
const val SUB_TITLE = "산타는 누구야?"
const val DESCRIPTION =
    "때는 2072년... 이제는 AI인지 사람인지 분간이 전혀 가질 않는다. 그런데… 그런 세상 속에서 이상형처럼 딱 맞는 그 사람을 만났다. 설마… 저 사람, AI는 아니겠지?"
const val BACKGROUND_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/icon/image"
const val ICON_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/description/image"
const val COVER_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/description/cover_image"
const val THEME = "산타"
const val PROMPT = "AI 산타 캐릭터를 만들거야. MBTI는 ESFP이고, 20대 초중반 정도의 젊은 산타였으면 좋겠어. 선물 고르는 센스가 남다르고, 공감을 잘하는 성격을 가진 캐릭터로 설정해줘."
const val CORRECT_ANSWER_COUNT = 1
const val PARTICIPANT_COUNT = 1

fun createExam(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION,
    prompt: String = PROMPT,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
    coverImage: String = COVER_IMAGE,
    theme: String = THEME,
    createdAt: LocalDateTime = CREATED_AT
): Exam =
    Exam(
        id = id,
        title = title,
        subTitle = subTitle,
        description = description,
        prompt = prompt,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        coverImage = coverImage,
        theme = theme,
        createdAt = createdAt
    )

fun createExamResult(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION,
    prompt: String = PROMPT,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
    coverImage: String = COVER_IMAGE,
    theme: String = THEME,
    isSolved: Boolean = false,
    createdAt: LocalDateTime = CREATED_AT
): ExamResult =
    ExamResult(
        id = id,
        title = title,
        subTitle = subTitle,
        description = description,
        prompt = prompt,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        coverImage = coverImage,
        theme = theme,
        isSolved = isSolved,
        createdAt = createdAt
    )

fun createStartExamResult(quizIds: List<Long> = listOf(ID)): StartExamResult = StartExamResult(quizIds = quizIds)

fun createSubmitExamResult(
    correctAnswerCount: Int = CORRECT_ANSWER_COUNT,
    badge: Badge = createBadge()
): SubmitExamResult =
    SubmitExamResult(
        correctAnswerCount = correctAnswerCount,
        badge = badge
    )
