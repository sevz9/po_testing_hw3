plugins {
    kotlin("jvm") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:lincheck-jvm:2.34")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}