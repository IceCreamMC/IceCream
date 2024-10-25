import java.util.*

rootProject.name = "IceCream"

listOf("IceCream-API", "IceCream-Server").forEach { project ->
    val name = project.lowercase(Locale.ENGLISH)
    include(name)
    findProject(":$name")?.projectDir = file(project)
}
