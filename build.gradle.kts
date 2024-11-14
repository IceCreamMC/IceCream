plugins {
    id("java")
    `maven-publish`
}

subprojects {
    plugins.apply("java")

    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }


publishing {
    repositories {
        maven {
            url = uri("https://repo.icecreammc.xyz/releases")
            credentials {
                username = System.getenv("REPOSILITE_USERNAME")
                password = System.getenv("REPOSILITE_PASSWORD")
            }
        }
    }

    publications {
        maven(MavenPublication) {
            groupId = "xyz.icecreammc.icecream"
            artifactId = "icecream-api"
            version = "1.0.0"
:
            artifact(file("IceCream-API/build/libs/icecream-api-1.21.1-SNAPSHOT.jar"))
        }
    }
}


    tasks {
        withType<JavaCompile> {
            options.encoding = Charsets.UTF_8.name()
            options.release = 21
        }
    }
}