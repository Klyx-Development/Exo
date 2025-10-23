plugins {
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    version = "1.0"
}

group = "org.klyx.exo"
description = "Klyx's packet entity API"