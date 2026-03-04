plugins {
    // kotlin("jvm") version "2.1.20"
    // Added the following two for serializing Experiment objects
    kotlin("jvm") version "2.2.0" // or kotlin("multiplatform") or any other kotlin plugin
    kotlin("plugin.serialization") version "2.2.0"
}

group = "com.idris"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.exposed.core)  // for Exposed
    implementation(libs.exposed.dao)   // for Exposed
    implementation(libs.exposed.jdbc)  // for Exposed
    implementation(libs.h2)            // for Exposed
    implementation("ch.qos.logback:logback-classic:1.5.13")  // Logging provider
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    // implementation("org.jetbrains.exposed:exposed-kotlin-datetime")
    //implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    //implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}


tasks.test {
    useJUnitPlatform()
}

tasks.jar.configure {
    manifest {
        attributes(mapOf("Main-Class" to "com.idris.MainKt"))
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<JavaExec> {
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}

kotlin {
    jvmToolchain(21)
}