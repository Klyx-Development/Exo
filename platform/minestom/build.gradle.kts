plugins {
    `java-library`
    `maven-publish`
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

dependencies {
    api(project(":common"))
    api(libs.minestom)
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
    options.release = 25
}

publishing {
    repositories {
        maven {
            name = "klyxReleases"
            url = uri("https://repo.klyx.org/releases")
            credentials {
                username = System.getProperty("KLYX_PRIVATE_USERNAME") ?: System.getenv("KLYX_PRIVATE_USERNAME")
                password = System.getProperty("KLYX_PRIVATE_PASSWORD") ?: System.getenv("KLYX_PRIVATE_PASSWORD")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.klyx.exo"
            artifactId = "minestom"
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
}
