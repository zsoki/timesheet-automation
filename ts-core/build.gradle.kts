plugins {
    kotlin("jvm") version Versions.kotlin
}

group = "hu.zsoki.ts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:${Versions.selenium}")
    implementation("com.uchuhimo:konf:${Versions.konf}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}