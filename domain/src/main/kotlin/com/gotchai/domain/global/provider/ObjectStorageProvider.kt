package com.gotchai.domain.global.provider

import org.springframework.web.multipart.MultipartFile

interface ObjectStorageProvider {
    fun uploadFile(key: String, file: MultipartFile): String
}
