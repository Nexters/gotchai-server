package com.gotchai.storage.redis.global.annotation

import org.springframework.stereotype.Component

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Component
annotation class Adapter
