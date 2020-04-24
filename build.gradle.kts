plugins {
    kotlin("jvm") version "1.3.61"
    application

    // ktlint formatter plugin
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //Ktor
    val ktorVersion = "1.3.0"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")

    // Kotlintest dependency
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.0")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    // To get rid of red warning when running TestMain.kt
    testImplementation("org.slf4j:slf4j-simple:1.7.26")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClassName = "Main.kt"
}

// Part of kotlintest
tasks.withType<Test> {
    useJUnitPlatform()
}
