package com.gotchai.storage.rdb.quiz.entity

import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.QuizPick
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Table
@Entity(name = "quiz_pick")
class QuizPickEntity(
    val quizId: Long,
    val contents: String,
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)")
    val answerType: AnswerType
) : BaseEntity() {
    companion object {
        fun from(creation: QuizPick.Creation): QuizPickEntity =
            with(creation) {
                QuizPickEntity(
                    quizId = quizId,
                    contents = contents,
                    answerType = answerType
                )
            }
    }

    fun toQuizContents() =
        QuizPick(
            id = id!!,
            quizId = quizId,
            contents = contents,
            type = answerType
        )
}
