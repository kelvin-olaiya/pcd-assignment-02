import org.gradle.configurationcache.extensions.capitalized

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
    implementation("io.vertx:vertx-core:4.4.1")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.72.Final:osx-aarch_64")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
}

var launchTypes = listOf("executors", "virtual", "eventLoop", "reactive")
launchTypes.forEach {
    tasks.create<JavaExec>("run${it.capitalized()}") {
        mainClass.set("Main")
        classpath = project.sourceSets.main.get().runtimeClasspath
        if (project.hasProperty("args")) {
            args(it, project.property("args") ?: "")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(listOf("--enable-preview"))
}

tasks.withType<JavaExec>().configureEach {
    jvmArguments.addAll("--enable-preview")
}
