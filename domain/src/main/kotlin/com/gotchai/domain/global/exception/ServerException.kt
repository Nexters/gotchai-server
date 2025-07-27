package com.gotchai.domain.global.exception

abstract class ServerException(
    val statusCode: Int,
    override val message: String,
) : RuntimeException(message)
