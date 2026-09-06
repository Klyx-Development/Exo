pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Exo"

include(":common")
include(":platform-paper")
project(":platform-paper").projectDir = file("platform/paper")
include(":platform-minestom")
project(":platform-minestom").projectDir = file("platform/minestom")
include(":demo-paper")
project(":demo-paper").projectDir = file("demo/paper")
include(":demo-minestom")
project(":demo-minestom").projectDir = file("demo/minestom")
