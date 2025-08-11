package com.gotchai.domain.global.provider

interface ObjectStorageProvider {
    fun uploadFile(key: String, file: ByteArray): String
}
