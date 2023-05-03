plugins {
    java
    application
}

application {
    mainClass.set("HelloWorld")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("--enable-preview"))
}

tasks.withType<JavaExec>().configureEach {
    jvmArguments.addAll("--enable-preview")
}