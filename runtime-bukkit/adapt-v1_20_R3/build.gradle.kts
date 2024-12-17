plugins {
    id("hephaestus.runtime-bukkit-conventions")
    id("hephaestus.publishing-conventions")
    id("io.papermc.paperweight.userdev") version "1.7.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")

    compileOnly("team.unnamed:creative-central-api:1.3.0")

    implementation(project(":hephaestus-api"))
    implementation(project(":hephaestus-reader-blockbench"))
    implementation(project(":hephaestus-runtime-bukkit-api"))
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
