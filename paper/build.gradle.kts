import org.gradle.kotlin.dsl.*
import sun.tools.jar.resources.jar
import java.io.File

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}

val envFile: File = rootProject.file(".env")
if (envFile.exists()) {
    envFile.forEachLine { line ->
        val trimmed = line.trim()
        if (trimmed.isBlank() || trimmed.startsWith("#")) return@forEachLine
        val parts = trimmed.split("=", limit = 2)
        if (parts.size != 2) return@forEachLine
        val key = parts[0].trim()
        val value = parts[1].trim()
        if (!System.getenv().containsKey(key)) {
            System.setProperty(key, value)
        }
    }
}

java {
    disableAutoTargetJvm()
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

dependencies {
    compileOnly(libs.paper.api)
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveFileName = "${rootProject.name}-${project.version}.jar"
        archiveClassifier = null

        manifest {
            attributes["Implementation-Version"] = rootProject.version
        }
    }

    assemble {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 25
    }

    withType<Javadoc>() {
        options.encoding = Charsets.UTF_8.name()
    }

    defaultTasks("build")
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
            artifactId = "exo"
            version = rootProject.version.toString()
            artifact(tasks.shadowJar)
        }
    }
}
