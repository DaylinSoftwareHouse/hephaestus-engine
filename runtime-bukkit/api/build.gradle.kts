plugins {
    id("hephaestus.runtime-bukkit-conventions")
    id("hephaestus.publishing-conventions")
    kotlin("jvm") version "1.9.22"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":hephaestus-api"))
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
