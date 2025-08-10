package com.gotchai.ai.adapter.out

import com.gotchai.ai.model.GeminiChatModel
import com.gotchai.domain.exam.dto.command.ChatMessage
import com.gotchai.domain.exam.port.out.GeminiModelPort
import org.springframework.stereotype.Component

@Component
class GeminiModelAdapter(
    private val geminiChatModel: GeminiChatModel
) : GeminiModelPort {
    override fun callGemini(
        prompt: String,
        contents: String
    ): ChatMessage {
        val response = geminiChatModel.generateContent(prompt, contents)
        return ChatMessage(response)
    }
}
