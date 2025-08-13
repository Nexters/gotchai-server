package com.gotchai.domain.global.provider

import com.gotchai.domain.global.dto.StorageObject

interface ObjectStorageProvider {
    fun uploadObject(
        path: String,
        `object`: StorageObject
    ): String
}
