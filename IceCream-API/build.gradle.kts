import java.lang.System

plugins {
    id("java-library")
    id("maven-publish")
    id("java")
}

val minecraftLibrary by configurations.registering

configurations.api {
    extendsFrom(minecraftLibrary.get())
}

dependencies {
    api(libs.bundles.adventure)
    api(libs.configurate)
    api(libs.guice) {
        exclude("com.google.guava", "guava")
    }
}

val generatedDir = "src/generated/java"

sourceSets {
    main {
        java {
            srcDir(generatedDir)
        }
    }
}

publishing {
  repositories {
    maven {
      name = "ICECREAM"
      url = uri("https://repo.icecreammc.xyz/releases")
      credentials {
        username = System.getEnv("MAVEN_USER")
        password = System.getEnv("MAVEN_SECRET")
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      groupId = "xyz.icecreammc"
      artifactId = "api"
      version = "1.21.1-build.1"
      from(components["java"])
    }
  }
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.register<JavaExec>("runApiGenerator") {
    doNotTrackState("Run api generator")

    mainClass = "xyz.icecreammc.icecream.api.generator.Main"
    classpath(project(":api-generator").sourceSets.main.get().runtimeClasspath)

    doFirst {
        workingDir(project.rootDir.resolve("run").apply { mkdirs() })

        val sourceFolder = project.projectDir.resolve(generatedDir).absoluteFile
        sourceFolder.deleteRecursively()

        val debugFlag = if (project.hasProperty("debug")) "-debug" else ""

        args("-sourceFolder=${sourceFolder}", debugFlag)
    }
}
