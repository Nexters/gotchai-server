package com.gotchai.api.fixture

import com.gotchai.api.presentation.v1.auth.request.AppleLoginRequest
import com.gotchai.api.presentation.v1.auth.request.KakaoLoginRequest
import com.gotchai.api.presentation.v1.auth.request.RefreshRequest
import com.gotchai.domain.fixture.TOKEN

fun createAppleLoginRequest(idToken: String = TOKEN): AppleLoginRequest = AppleLoginRequest(idToken)

fun createKakaoLoginRequest(accessToken: String = TOKEN): KakaoLoginRequest = KakaoLoginRequest(accessToken)

fun createRefreshRequest(refreshToken: String = TOKEN): RefreshRequest = RefreshRequest(refreshToken)
