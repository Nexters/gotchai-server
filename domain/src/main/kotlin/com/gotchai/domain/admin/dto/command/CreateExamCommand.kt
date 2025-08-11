package com.gotchai.domain.admin.dto.command

import org.springframework.web.multipart.MultipartFile

class CreateExamCommand(
    val title: String,
    val subTitle: String,
    val description: String,
    val prompt: String,
    val backgroundImageFile: MultipartFile,
    val iconImageFile: MultipartFile,
    val coverImageFile: MultipartFile,
    val theme: String
)
