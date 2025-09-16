plugins {
    kotlin("jvm") version "2.1.20"
}

group = "com.idriseus"
version = "1.0-SNAPSHOT"

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
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}