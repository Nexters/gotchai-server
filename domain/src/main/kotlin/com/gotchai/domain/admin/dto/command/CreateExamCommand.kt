package com.gotchai.domain.admin.dto.command

import com.gotchai.domain.global.dto.StorageObject

data class CreateExamCommand(
    val title: String,
    val subTitle: String,
    val description: String,
    val prompt: String,
    val backgroundImage: StorageObject,
    val iconImage: StorageObject,
    val coverImage: StorageObject,
    val theme: String
)
