package com.gotchai.storage.rdb.quiz.entity

import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table
@Entity(name = "quiz")
class QuizEntity(
    val examId: Long,
    val contents: String,
    @Column(name = "\"order\"")
    val order: Int
) : BaseEntity() {
    companion object {
        fun from(creation: Quiz.Creation): QuizEntity =
            with(creation) {
                QuizEntity(
                    examId = examId,
                    contents = contents,
                    order = order
                )
            }
    }

    fun toQuiz() =
        Quiz(
            id = id!!,
            examId = examId,
            contents = contents,
            order = order,
            createdAt = createdAt
        )
}
