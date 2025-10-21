plugins {
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

group = "org.klyx.exo"
version = "1.0"
description = "Klyx's packet entity API"