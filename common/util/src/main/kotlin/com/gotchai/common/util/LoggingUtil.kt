package com.gotchai.common.util

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

inline fun <reified T> T.getLogger(): KLogger = KotlinLogging.logger {}
