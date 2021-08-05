plugins {
    application
    kotlin("jvm") version "1.5.21"
    id("org.openjfx.javafxplugin") version "0.0.10"
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
    implementation(project(":ts-crawler"))

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
//            apiVersion = "1.1"
        }
    }
}
