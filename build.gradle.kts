plugins {
    java
    application
}

application {
    mainClass.set("HelloWorld")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("--enable-preview"))
}

tasks.withType<JavaExec>().configureEach {
    jvmArguments.addAll("--enable-preview")
}