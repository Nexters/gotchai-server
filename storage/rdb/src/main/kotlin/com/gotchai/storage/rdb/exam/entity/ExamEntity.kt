package com.gotchai.storage.rdb.exam.entity

import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "exam")
class ExamEntity(
    val title: String,
    val subTitle: String,
    val descriptionImage: String,
): BaseEntity() {
}