package com.gotchai.domain.admin.adapter.`in`

import com.gotchai.domain.admin.dto.command.CreateExamCommand
import com.gotchai.domain.admin.port.`in`.AdminCommandUseCase
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamCommandPort
import com.gotchai.domain.global.provider.ObjectStorageProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AdminCommandService(
    private val examCommandPort: ExamCommandPort,
    private val objectStorageProvider: ObjectStorageProvider
) : AdminCommandUseCase {
    @Transactional
    override fun createExam(command: CreateExamCommand): Exam =
        with(command) {
            val (iconImage, coverImage, backgroundImage) =
                listOf(iconImageFile, coverImageFile, backgroundImageFile)
                    .map { objectStorageProvider.uploadFile("exam/${UUID.randomUUID()}.png", it) }

            examCommandPort.createExam(
                Exam.Creation(
                    title = title,
                    subTitle = subTitle,
                    description = description,
                    prompt = prompt,
                    backgroundImage = backgroundImage,
                    iconImage = iconImage,
                    coverImage = coverImage,
                    theme = theme
                )
            )
        }
}
