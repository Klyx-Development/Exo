plugins {
    alias(libs.plugins.paperweight.userdev)
}

allprojects {
    plugins.apply(rootProject.libs.plugins.paperweight.userdev.get().pluginId)

    dependencies {
        paperweight.paperDevBundle(rootProject.libs.versions.paper.api.get())
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven { url = uri("https://repo.bitsquidd.xyz/repository/bit/") }
    }

    version = "2.0.0"
}

group = "org.klyx.exo"
description = "Klyx's packet entity API"