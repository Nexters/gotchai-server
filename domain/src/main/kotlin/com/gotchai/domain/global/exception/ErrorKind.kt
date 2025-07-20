package com.gotchai.domain.global.exception

enum class ErrorKind {
    SERVER_ERROR, // 500
    INTERNAL_SERVER_ERROR, // 500
    CLIENT_ERROR,
    AUTHORIZATION,
    NOT_FOUND,
    FORBIDDEN_ERROR,
    CONFLICT,
    METHOD_ARGUMENT_TYPE_MISMATCH,
    METHOD_NOT_ALLOWED,
}
