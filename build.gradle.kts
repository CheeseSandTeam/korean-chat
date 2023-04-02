import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.Version
    id("io.papermc.paperweight.userdev") version "1.3.8"
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:${Dependency.PaperAPI.Version}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Coroutine.Version}")
    paperDevBundle(Dependency.PaperAPI.Version)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}

java {
    toolchain.apply {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val pluginName = rootProject.name.capitalize()
val packageName = rootProject.name

val aliasName = packageName

extra.apply {
    set("pluginName", pluginName)
    set("packageName", packageName)
    set("aliasName", aliasName)

    set("kotlinVersion", Dependency.Kotlin.Version)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }

    register<Jar>("pluginJar") {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        from(sourceSets["main"].output)
    }
}
