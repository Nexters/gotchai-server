package com.gotchai.api.presentation.v1.admin.request

import com.gotchai.api.global.util.toStorageObject
import com.gotchai.domain.admin.dto.command.CreateExamCommand
import org.springframework.web.multipart.MultipartFile

data class CreateExamRequest(
    val title: String,
    val subTitle: String,
    val description: String,
    val prompt: String,
    val backgroundImage: MultipartFile,
    val iconImage: MultipartFile,
    val coverImage: MultipartFile,
    val theme: String
) {
    fun toCommand(): CreateExamCommand =
        CreateExamCommand(
            title = title,
            subTitle = subTitle,
            description = description,
            prompt = prompt,
            backgroundImage = backgroundImage.toStorageObject(),
            iconImage = iconImage.toStorageObject(),
            coverImage = coverImage.toStorageObject(),
            theme = theme
        )
}
