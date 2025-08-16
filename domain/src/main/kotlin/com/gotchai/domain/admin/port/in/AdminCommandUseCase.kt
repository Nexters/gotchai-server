package com.gotchai.domain.admin.port.`in`

import com.gotchai.domain.admin.dto.command.CreateExamCommand
import com.gotchai.domain.exam.entity.Exam

interface AdminCommandUseCase {
    fun createExam(command: CreateExamCommand): Exam
}
