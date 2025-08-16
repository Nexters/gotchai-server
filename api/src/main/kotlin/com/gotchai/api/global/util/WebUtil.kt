package com.gotchai.api.global.util

import com.gotchai.domain.global.dto.Extension
import com.gotchai.domain.global.dto.StorageObject
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

internal fun MultipartFile.toStorageObject(): StorageObject =
    StorageObject(
        extension = StringUtils.getFilenameExtension(originalFilename)?.let { Extension.valueOf(it.uppercase()) },
        size = size,
        stream = inputStream
    )
