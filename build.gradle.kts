plugins {
    kotlin("jvm") version "2.1.20"
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
    //implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    //implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
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

kotlin {
    jvmToolchain(21)
}