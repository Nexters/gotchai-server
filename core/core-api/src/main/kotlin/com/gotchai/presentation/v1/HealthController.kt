package com.gotchai.presentation.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class HealthController {
    @GetMapping("/ping")
    fun healthCheck(): PongResponse = PongResponse(LocalDateTime.now())

    data class PongResponse(
        val now: LocalDateTime,
    )
}
