package com.gotchai.storage.rdb.global.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataSourceConfig {
    @Bean("coreHikariConfig")
    @ConfigurationProperties(prefix = "datasource.rdb")
    fun hikariConfig(): HikariConfig = HikariConfig()

    @Bean("coreDataSource")
    fun dataSource(): HikariDataSource = HikariDataSource(hikariConfig())
}
