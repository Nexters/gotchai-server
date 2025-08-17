package com.gotchai.api.global.exception

import com.gotchai.common.util.getLogger
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    companion object {
        private val log = getLogger()
    }

    override fun handleUncaughtException(
        ex: Throwable,
        method: Method,
        vararg params: Any?
    ) {
        log.error(ex) { "Exception : ${ex.message}" }
    }
}
