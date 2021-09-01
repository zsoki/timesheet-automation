plugins {
    application
    kotlin("jvm") version Versions.kotlin
    id(Dependencies.javaFxPlugin) version Versions.openJfx
}

group = "hu.zsoki.ts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":ts-core"))

    implementation(Dependencies.tornadoFx2) {
        exclude("org.jetbrains.kotlin")
        exclude("org.openjfx")
    }
    implementation(Dependencies.konf)

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
