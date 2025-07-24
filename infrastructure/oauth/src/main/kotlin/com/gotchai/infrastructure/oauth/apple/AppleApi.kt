package com.gotchai.infrastructure.oauth.apple

import com.gotchai.infrastructure.oauth.apple.response.ApplePublicKeysResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "apple-api", url = "https://appleid.apple.com")
interface AppleApi {
    @GetMapping("/auth/keys")
    fun getApplePublicKeys(): ApplePublicKeysResponse
}
