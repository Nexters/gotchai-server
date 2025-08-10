package com.gotchai.ai.model

import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse
import com.google.gson.Gson
import com.gotchai.ai.config.GeminiProperties
import org.springframework.stereotype.Component

@Component
class GeminiChatModel(
    private val geminiProperties: GeminiProperties
) {
    private val gson = Gson()
    private val client: Client =
        Client
            .builder()
            .apiKey(geminiProperties.apiKey)
            .build()

    fun generateContent(
        prompt: String,
        contents: String
    ): String? {
        val fullPrompt = "상황: $prompt\n\n질문: $contents\n\n위 상황을 바탕으로 질문에 대해 답변해주세요."
        return try {
            val response =
                client.models.generateContent(
                    geminiProperties.model,
                    fullPrompt,
                    GenerateContentConfig
                        .builder()
                        .temperature(0.3f)
                        .maxOutputTokens(1000)
                        .topP(0.9f)
                        .topK(25f)
                        .candidateCount(1)
                        .responseMimeType("application/json")
                        .build()
                )
            getResponseText(response)
        } catch (e: Exception) {
            null
        }
    }

    fun getResponseText(response: GenerateContentResponse?): String? =
        try {
            val text =
                response
                    ?.candidates()
                    ?.get()
                    ?.first()
                    ?.content()
                    ?.get()
                    ?.text()
            val jsonResponse = gson.fromJson(text, Map::class.java)["response"] as? String
            println("jsonResponse: $jsonResponse")
            jsonResponse
        } catch (e: Exception) {
            null
        }
}
