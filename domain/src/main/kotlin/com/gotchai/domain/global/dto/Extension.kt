package com.gotchai.domain.global.dto

enum class Extension(
    val dotNotation: String
) {
    PNG(".png"),
    WEBP(".webp"),
    AVIF(".avif");

    companion object {
        private val IMAGE_EXTENSIONS = setOf(PNG, WEBP, AVIF)
    }

    val isImage: Boolean
        get() = this in IMAGE_EXTENSIONS
}
