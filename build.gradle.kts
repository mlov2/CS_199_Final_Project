plugins {
    kotlin("jvm") version "1.3.61"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //Kotlintest dependency
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.0")

    //To get rid of red warning when running TestMain.kt
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

//Part of kotlintest
tasks.withType<Test> {
    useJUnitPlatform()
}