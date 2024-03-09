import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

if (!file(".git").exists()) {
    val errorText = """
        
        =====================[ ERROR ]=====================
         The IceCream project directory is not a properly cloned Git repository.
         
         In order to build IceCream from source you must clone
         the repository using Git, not download a code zip from GitHub.
         
         See https://icecreammc.gitbook.io/docs/developers/contributing
         for further information on building and modifying IceCream.
        ===================================================
    """.trimIndent()
    error(errorText)
}

rootProject.name = "icecream"

for (name in listOf("IceCream-API", "IceCream-Server", "paper-api-generator")) {
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
    findProject(":$projName")!!.projectDir = file(name)
}
