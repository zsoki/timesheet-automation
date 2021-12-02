plugins {
    kotlin("jvm") version Versions.kotlin
}

group = "hu.zsoki.ts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ts-config"))

    // Crawler
    implementation(Dependencies.seleniumJava)

    // Config
    implementation(Dependencies.konf)

    // Data
    implementation(Dependencies.sqlite)
    implementation(Dependencies.exposedCore)
    implementation(Dependencies.exposedJdbc)
    implementation(Dependencies.exposedDao)
    implementation(Dependencies.exposedJavaTime)
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}