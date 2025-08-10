pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "gotchai"

include("api", "domain")
include("common:util", "common:logging")
include("storage:rdb", "storage:redis")
include(
    "infrastructure:aws",
    "infrastructure:ncp",
    "infrastructure:oauth",
    "infrastructure:ai"
)
