import me.glicz.airplane.task.PaperclipJar
import java.time.Instant
import org.eclipse.jgit.api.Git

plugins {
    id("me.glicz.airplane")
    id("net.kyori.indra.git") version "3.1.3"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-releases/")
}

configurations.apiElements {
    extendsFrom(configurations.implementation.get())
}

rootProject.layout.projectDirectory.asFile.absolutePath

dependencies {
    mache(paperMache(properties["mache-build"] as String))
    paperclip(libs.paperclip)
    implementation(project(":api"))
    implementation("org.yaml:snakeyaml:2.3")
    implementation("org.spongepowered:mixin:0.8.7")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    testImplementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("org.bstats:bstats-base:3.1.0")
    implementation(libs.adventure.text.serializer.ansi)
    implementation("org.eclipse.jgit:org.eclipse.jgit:7.1.0.202411261347-r")
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

tasks.jar {
    manifest {
        val git = Git.open(rootProject.layout.projectDirectory.asFile)

        val gitHash = git.repository.resolve("HEAD").abbreviate(7).name()
        val gitBranch = git.repository.branch
        val gitCommitDate = git.log().setMaxCount(1).call().first().committerIdent.getWhen().toInstant()

        val mcVersion = rootProject.providers.gradleProperty("mcVersion").orNull ?: "UNKNOWN"
        val build = System.getenv("BUILD_NUMBER")
        val buildTime = if (build != null) Instant.now() else Instant.EPOCH

        val implementationVersion = "$mcVersion-${build ?: "DEV"}-$gitHash"

        attributes(
            "Main-Class" to "net.minecraft.server.Main",
            "Implementation-Title" to "IceCream",
            "Implementation-Version" to implementationVersion,
            "Implementation-Vendor" to gitCommitDate.toString(),
            "Specification-Title" to "IceCream",
            "Specification-Version" to project.version,
            "Specification-Vendor" to "IceCreamMC Team",
            "Brand-Id" to "icecreammc:icecream",
            "Brand-Name" to "IceCream",
            "Build-Number" to (build ?: ""),
            "Build-Time" to buildTime.toString(),
            "Git-Branch" to gitBranch,
            "Git-Commit" to gitHash
        )
    }
}

tasks.compileJava {
    options.compilerArgs.add("-Xlint:-deprecation")
    options.isWarnings = false
}

airplane {
    minecraftVersion = properties["minecraft-version"] as String
    sourcesDir = projectDir.resolve(internalsDir)
    patchesDir = projectDir.resolve("patches").apply { mkdirs() }

    // export some of the dependencies to api in order to avoid version mismatch
    exportFilteredDependencies = mapOf(
        project(":api") to listOf(
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

afterEvaluate {
    tasks {
        withType<PaperclipJar> {
            dependsOn(":api:jar")
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