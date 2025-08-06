package com.gotchai.domain.fixture

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamResult
import java.time.LocalDateTime

const val TITLE = "AI와 크리스마스 파티"
const val SUB_TITLE = "산타는 누구야?"
const val DESCRIPTION_EXAM =
    "때는 2072년...\u2028이제는 AI인지 사람인지 분간이 전혀 가질 않는다.\u2028그런데… 그런 세상 속에서 이상형처럼\u2028딱 맞는 그 사람을 만났다!\n" +
        "설마… 저 사람, AI는 아니겠지?"
const val BACKGROUND_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/icon/image"
const val ICON_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/description/image"
const val THEME = "blue"
const val PROMPT = "AI 산타 캐릭터를 만들거야. MBTI는 ESFP이고, 20대 초중반 정도의 젊은 산타였으면 좋겠어. 선물 고르는 센스가 남다르고, 공감을 잘하는 성격을 가진 캐릭터로 설정해줘."
const val DEFAULT_TAKE_QUIZ_IDS = "1,2,3,4,5"
const val DEFAULT_ANSWER_QUIZ_IDS = "1,3,5"
const val DEFAULT_FAILED_QUIZ_IDS = "2,4"

fun createExam(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION_EXAM,
    prompt: String = PROMPT,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
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
        theme = theme,
        createdAt = createdAt
    )

fun createExamResult(
    id: Long = ID,
    examId: Long = ID,
    userId: Long = ID,
    takeQuizIds: String = DEFAULT_TAKE_QUIZ_IDS,
    answerQuizIds: String? = DEFAULT_ANSWER_QUIZ_IDS,
    failedQuizIds: String? = DEFAULT_FAILED_QUIZ_IDS,
    createdAt: LocalDateTime = CREATED_AT
): ExamResult =
    ExamResult(
        id = id,
        examId = examId,
        userId = userId,
        takeQuizIds = takeQuizIds.split(",").map { it.toLong() },
        answerQuizIds = answerQuizIds?.split(",")?.map { it.toLong() },
        failedQuizIds = failedQuizIds?.split(",")?.map { it.toLong() },
        createdAt = createdAt
    )

fun createExamResultCreation(
    examId: Long = ID,
    userId: Long = ID,
    takeQuizIds: String = DEFAULT_TAKE_QUIZ_IDS,
    answerQuizIds: String? = DEFAULT_ANSWER_QUIZ_IDS,
    failedQuizIds: String? = DEFAULT_FAILED_QUIZ_IDS
): ExamResult.Creation =
    ExamResult.Creation(
        examId = examId,
        userId = userId,
        takeQuizIds = takeQuizIds,
        answerQuizIds = answerQuizIds,
        failedQuizIds = failedQuizIds
    )

fun createGetExamResult(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION_EXAM,
    prompt: String = PROMPT,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
    theme: String = THEME,
    quizIds: List<Long> = listOf(ID),
    createdAt: LocalDateTime = CREATED_AT
): GetExamResult =
    GetExamResult(
        id = id,
        title = title,
        subTitle = subTitle,
        description = description,
        prompt = prompt,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        theme = theme,
        quizIds = quizIds,
        createdAt = createdAt
    )
