package com.gotchai.api.presentation.v1.auth.request

import com.gotchai.domain.auth.dto.command.RefreshCommand

data class RefreshRequest(
    val refreshToken: String
) {
    fun toCommand(): RefreshCommand = RefreshCommand(refreshToken = refreshToken)
}
