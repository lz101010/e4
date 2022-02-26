import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    kotlin("jvm") version "1.6.10"
    java

    // pretty print tests
    id("com.adarshr.test-logger") version "3.1.0"

    // test coverage
    jacoco

    // add support for building a CLI application
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.6.1")
}

application {
    mainClass.set("com.lz101010.AppKt")
}

tasks.test {
    useJUnitPlatform()
}

configure<TestLoggerExtension> {
    theme = ThemeType.MOCHA
    logLevel = LogLevel.LIFECYCLE
}

tasks.jacocoTestReport {
    reports {
        csv.isEnabled = true
        html.isEnabled = false
        xml.isEnabled = false

        csv.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.csv"))
    }
    dependsOn(tasks.check)
}