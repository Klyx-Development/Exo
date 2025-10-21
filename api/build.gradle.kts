import org.gradle.kotlin.dsl.*

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperweight.userdev)
}

java {
    disableAutoTargetJvm()
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper.api.get())

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
        options.release = 21
    }

    withType<Javadoc>() {
        options.encoding = Charsets.UTF_8.name()
    }

    defaultTasks("build")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

        }
    }
}