import me.glicz.airplane.task.PaperclipJar

plugins {
    id("me.glicz.airplane")
    id("net.kyori.indra.git") version "3.1.3"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.minebench.de/")
    maven("https://libraries.minecraft.net/")
}

configurations.apiElements {
    extendsFrom(configurations.implementation.get())
}

dependencies {
    mache(papierMache(properties["mache-build"] as String))
    paperclip("io.papermc:paperclip:3.0.3")
    implementation(project(":icecream-api"))
    implementation("com.mojang:brigadier:1.0.500")
    implementation("com.mojang:jtracy:1.0.29")
    implementation("com.microsoft.azure:msal4j:1.17.2")
    implementation(libs.adventure.text.serializer.ansi)
    implementation(libs.jline.terminal)
    implementation(libs.terminalConsoleAppender)
    annotationProcessor(libs.log4j.core)
}

val internalsDir = "src/internals/java"

sourceSets {
    main {
        java {
            srcDir(internalsDir)
        }
    }
}

airplane {
    minecraftVersion = properties["minecraft-version"] as String
    sourcesDir = projectDir.resolve(internalsDir)
    patchesDir = projectDir.resolve("patches").apply { mkdirs() }
    exportFilteredDependencies = mapOf(
        project(":icecream-api") to listOf(
            "com.google.guava:guava",
            "org.slf4j:slf4j-api",
            "com.mojang:brigadier"
        )
    )
}

tasks {
    jar {
        indraGit.applyVcsInformationToManifest(manifest)
    }
}

tasks.compileJava {
    options.compilerArgs.add("-Xlint:-deprecation")
    options.isWarnings = false
}

afterEvaluate {
    tasks {
        withType<PaperclipJar> {
            dependsOn(":icecream-api:jar")
        }

        named<JavaExec>("runServer") {
            project(":test-plugin").tasks.jar.let { testPluginJar ->
                dependsOn(testPluginJar)

                testPluginJar.get().destinationDirectory.set(
                    project.rootDir.resolve("run/plugins").apply {
                        deleteRecursively()
                    }
                )
            }
        }
    }
}
