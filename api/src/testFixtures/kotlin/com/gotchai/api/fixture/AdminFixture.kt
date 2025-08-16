package com.gotchai.api.fixture

import com.gotchai.api.presentation.v1.admin.request.CreateExamRequest
import com.gotchai.domain.fixture.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import kotlin.random.Random

val IMAGE_FILE = MockMultipartFile("image", "image.png", "image/png", Random.nextBytes(10))

fun createCreateExamRequest(
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION,
    prompt: String = PROMPT,
    backgroundImage: MultipartFile = IMAGE_FILE,
    iconImage: MultipartFile = IMAGE_FILE,
    coverImage: MultipartFile = IMAGE_FILE,
    theme: String = THEME
): CreateExamRequest =
    CreateExamRequest(
        title = title,
        subTitle = subTitle,
        description = description,
        prompt = prompt,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        coverImage = coverImage,
        theme = theme
    )
