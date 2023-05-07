plugins {
    java
    application
}

application {
    mainClass.set("Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("--enable-preview"))
}

tasks.withType<JavaExec>().configureEach {
    jvmArguments.addAll("--enable-preview")
}