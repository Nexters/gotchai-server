package com.gotchai.storage.rdb.quiz.entity

import com.gotchai.domain.quiz.entity.QuizContents
import com.gotchai.domain.quiz.entity.QuizType
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table
@Entity(name = "quiz_contents")
class QuizContentsEntity(
    val quizId: Long,
    val contents: String,
    val type: QuizType,
) : BaseEntity() {
    companion object {
        fun from(creation: QuizContents.Creation): QuizContentsEntity =
            with(creation) {
                QuizContentsEntity(
                    quizId = quizId,
                    contents = contents,
                    type = type,
                )
            }
    }

    fun toQuizContents() =
        QuizContents(
            id = id!!,
            quizId = quizId,
            contents = contents,
            type = type,
        )
}
