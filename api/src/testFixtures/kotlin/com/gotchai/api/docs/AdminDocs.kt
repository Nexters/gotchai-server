package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.admin.request.CreateExamRequest
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf

val createExamRequestFields =
    fieldsOf(
        CreateExamRequest::title desc "제목",
        CreateExamRequest::subTitle desc "부제목",
        CreateExamRequest::description desc "테스트 설명",
        CreateExamRequest::prompt desc "테스트 프롬프트",
        CreateExamRequest::backgroundImage desc "배경 이미지",
        CreateExamRequest::iconImage desc "아이콘 이미지",
        CreateExamRequest::coverImage desc "커버 이미지",
        CreateExamRequest::theme desc "테마"
    )
