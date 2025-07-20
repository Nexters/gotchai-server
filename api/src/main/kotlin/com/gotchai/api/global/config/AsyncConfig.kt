package com.gotchai.api.global.config

import com.gotchai.api.global.exception.AsyncExceptionHandler
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor =
        ThreadPoolTaskExecutor()
            .apply {
                corePoolSize = 10
                maxPoolSize = 10
                queueCapacity = 10000
                setWaitForTasksToCompleteOnShutdown(true)
                setAwaitTerminationSeconds(10)
                initialize()
            }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler = AsyncExceptionHandler()
}
