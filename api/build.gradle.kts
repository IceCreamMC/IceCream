plugins {
    id("java-library")
    id("maven-publish")
}

val minecraftLibrary by configurations.registering

configurations.api {
    extendsFrom(minecraftLibrary.get())
}

dependencies {
    api(libs.bundles.adventure)
    api(libs.configurate)
    api("org.yaml:snakeyaml:2.3")
    implementation("org.spongepowered:mixin:0.8.7")
    api(libs.guice) {
        exclude("com.google.guava", "guava")
    }
}

publishing {
  repositories {
    maven {
      name = "ICECREAM"
      url = uri("https://repo.icecreammc.xyz/releases")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      groupId = "xyz.icecreammc"
      artifactId = "api"
      version = "1.21.4-build.1"
      from(components["java"])
    }
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

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.register<JavaExec>("runApiGenerator") {
    doNotTrackState("Run api generator")

    mainClass = "me.glicz.airflow.api.generator.Main"
    classpath(project(":api-generator").sourceSets.main.get().runtimeClasspath)

    doFirst {
        workingDir(project.rootDir.resolve("run").apply { mkdirs() })

        val sourceFolder = project.projectDir.resolve(generatedDir).absoluteFile
        sourceFolder.deleteRecursively()

        val debugFlag = if (project.hasProperty("debug")) "-debug" else ""

        args("-sourceFolder=${sourceFolder}", debugFlag)
    }
}