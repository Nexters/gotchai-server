pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "gotchai"

include(
    "core:core-api",
    "core:core-domain",
    "core:core-utils",
)

include(
    "storage:rds",
)

include(
    "external:aws",
    "external:ncp",
)