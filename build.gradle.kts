plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

group = "top.colter.live"
version = "0.0.1"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    val ktorVersion = "1.6.7"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
