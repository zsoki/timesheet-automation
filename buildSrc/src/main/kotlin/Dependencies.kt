object Versions {
    val kotlin = "1.7.10"

    val openJfx = "0.0.13"
    val tornadoFx2 = "master"
    val konf = "1.1.2"

    val selenium = "4.3.0"
    val sqlite = "3.36.0.3"
    val exposed = "0.38.2"
}

object Dependencies {
    val javaFxPlugin = "org.openjfx.javafxplugin"
    val tornadoFx2 = "com.github.edvin:tornadofx2:${Versions.tornadoFx2}"
    val konf = "com.uchuhimo:konf:${Versions.konf}"

    val seleniumJava = "org.seleniumhq.selenium:selenium-java:${Versions.selenium}"
    val sqlite = "org.xerial:sqlite-jdbc:${Versions.sqlite}"
    val exposedCore = "org.jetbrains.exposed:exposed-core:${Versions.exposed}"
    val exposedDao = "org.jetbrains.exposed:exposed-dao:${Versions.exposed}"
    val exposedJdbc = "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}"
    val exposedJavaTime = "org.jetbrains.exposed:exposed-java-time:${Versions.exposed}"
}