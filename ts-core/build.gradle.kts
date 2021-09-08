plugins {
    kotlin("jvm") version Versions.kotlin
}

group = "hu.zsoki.ts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ts-data"))

    implementation(Dependencies.seleniumJava)
    implementation(Dependencies.konf)
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}