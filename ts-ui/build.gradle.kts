plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id("org.openjfx.javafxplugin") version Versions.openJfx
}

group = "hu.zsoki.ts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.edvin:tornadofx2:master") {
        exclude("org.jetbrains.kotlin")
        exclude("org.openjfx")
    }
    implementation(project(":ts-core"))
    implementation("com.uchuhimo:konf:${Versions.konf}")

    testImplementation(kotlin("test"))
}

application {
    mainClass.set("hu.zsoki.ts.ui.MainKt")
}

javafx {
    modules = listOf("javafx.controls", "javafx.graphics")
}

tasks {
    test {
        useJUnitPlatform()
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
