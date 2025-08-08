package com.gotchai.storage.rdb.exam.entity

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "exam")
class ExamEntity(
    @Column(length = 50)
    val title: String,
    @Column(length = 50)
    val subTitle: String,
    val description: String,
    @Column(columnDefinition = "TEXT")
    val prompt: String,
    val backgroundImage: String,
    val coverImage: String,
    val iconImage: String,
    val theme: String
) : BaseEntity() {
    companion object {
        fun from(creation: Exam.Creation): ExamEntity =
            with(creation) {
                ExamEntity(
                    title = title,
                    subTitle = subTitle,
                    description = description,
                    prompt = prompt,
                    backgroundImage = backgroundImage,
                    iconImage = iconImage,
                    coverImage = coverImage,
                    theme = theme
                )
            }
    }

    fun toExam() =
        Exam(
            id = id!!,
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
}
