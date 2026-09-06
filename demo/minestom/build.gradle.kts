plugins {
    application
    alias(libs.plugins.shadow)
}

application {
    mainClass.set("org.klyx.exo.minestom.demo.ExoMinestomDemo")
}

dependencies {
    implementation(project(":platform-minestom"))
    runtimeOnly(libs.slf4j.simple)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks.shadowJar {
    archiveFileName.set("ExoMinestomDemo.jar")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    mergeServiceFiles()
}



