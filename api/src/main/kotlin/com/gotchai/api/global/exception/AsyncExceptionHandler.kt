package com.gotchai.api.global.exception

import com.gotchai.common.util.logger
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    private val log by logger()

    override fun handleUncaughtException(
        e: Throwable,
        method: Method,
        vararg params: Any?
    ) {
        log.error("Exception : {}", e.message, e)
    }
}
