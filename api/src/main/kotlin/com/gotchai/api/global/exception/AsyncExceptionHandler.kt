package com.gotchai.api.global.exception

import com.gotchai.common.util.logger
import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorLevel
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    private val log by logger()

    override fun handleUncaughtException(
        e: Throwable,
        method: Method,
        vararg params: Any?,
    ) {
        if (e is ErrorException) {
            when (e.errorType.level) {
                ErrorLevel.ERROR -> log.error("ErrorException : {}", e.message, e)
                ErrorLevel.WARN -> log.warn("ErrorException : {}", e.message, e)
                else -> log.info("ErrorException : {}", e.message, e)
            }
        } else {
            log.error("Exception : {}", e.message, e)
        }
    }
}
