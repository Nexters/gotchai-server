package com.gotchai.storage.rdb.exam.entity

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.storage.rdb.global.common.BaseEntity
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
    val descriptionImage: String,
    val iconImage: String,
    val theme: String,
) : BaseEntity() {
    companion object {
        fun from(creation: Exam.Creation): ExamEntity =
            with(creation) {
                ExamEntity(
                    title = title,
                    subTitle = subTitle,
                    descriptionImage = descriptionImage,
                    iconImage = iconImage,
                    theme = theme,
                )
            }
    }
}
