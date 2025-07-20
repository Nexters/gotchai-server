package com.gotchai.storage.rdb.global.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.gotchai.storage.rdb"])
@EnableJpaRepositories(basePackages = ["com.gotchai.storage.rdb"])
class JpaConfig
