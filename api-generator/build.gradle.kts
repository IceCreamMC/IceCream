dependencies {
    implementation(project(":icecream-server"))
    implementation("com.squareup:javapoet:1.13.0")
    implementation("org.apache.maven.plugins:maven-javadoc-plugin:3.11.1")
}

tasks.compileJava {
    options.compilerArgs.add("-Xlint:-deprecation")
    options.isWarnings = false
}
