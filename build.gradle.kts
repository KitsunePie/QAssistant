// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion: String by project
    val gradleVersion: String by project
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
