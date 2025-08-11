package com.gotchai.domain.admin.dto.command

class CreateExamCommand(
    val title: String,
    val subTitle: String,
    val description: String,
    val prompt: String,
    val backgroundImageFile: ByteArray,
    val iconImageFile: ByteArray,
    val coverImageFile: ByteArray,
    val theme: String
)
