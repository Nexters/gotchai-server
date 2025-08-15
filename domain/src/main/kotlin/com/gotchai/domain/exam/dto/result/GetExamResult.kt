package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamHistory

data class GetExamResult(
    val id: Long,
    val title: String,
    val subTitle: String,
    val iconImage: String,
    val isSolved: Boolean
) {
    companion object {
        fun of(
            exam: Exam,
            examHistory: ExamHistory
        ): GetExamResult =
            GetExamResult(
                id = exam.id,
                title = exam.title,
                subTitle = exam.subTitle,
                iconImage = exam.iconImage,
                isSolved = examHistory.isSolved
            )
    }
}
