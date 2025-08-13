package com.gotchai.domain.global.dto

import java.io.InputStream

data class StorageObject(
    val extension: Extension?,
    val size: Long,
    val stream: InputStream
)
