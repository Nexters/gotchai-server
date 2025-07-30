package com.gotchai.api.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.gotchai"])
@ComponentScan(basePackages = ["com.gotchai"])
class ScanConfig
