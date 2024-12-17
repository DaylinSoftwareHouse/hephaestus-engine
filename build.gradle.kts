allprojects {
    repositories {
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = file("${project.rootDir}/.gradle/gradle.properties").readText().split("=", limit=2).last()
            }
        }
    }
}

subprojects {
    plugins.apply("java")
    plugins.apply("maven-publish")

    configure<PublishingExtension> {
        publications.create("mavenJava", MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])

            pom {
                url.set("https://github.com/DaylinSoftwareHouse/hephaestus-engine")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("DaylightNebula")
                        name.set("Noah Shaw")
                        email.set("noah.w.shaw@gmail.com")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/DaylinSoftwareHouse/hephaestus-engine")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
