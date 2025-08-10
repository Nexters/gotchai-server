package com.gotchai.domain.exam.port.out

import com.gotchai.domain.exam.dto.command.ChatMessage

interface GeminiModelPort {
    fun callGemini(
        prompt: String,
        contents: String
    ): ChatMessage
}
