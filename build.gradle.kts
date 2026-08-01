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
    }

    version = "2.0.3"
}

group = "org.klyx.exo"
description = "Klyx's packet entity API"