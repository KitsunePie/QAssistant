/*
 * QAssistant - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2021
 * https://github.com/KitsunePie/QAssistant
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation and our eula published by us;
 *  either version 3 of the License, or any later version and our eula as published
 * by us.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/KitsunePie/QAssistant/blob/master/LICENSE.md>.
 */
val kspVersion: String by project

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 32
    buildToolsVersion = "32.0.0"

    defaultConfig {
        applicationId = "org.kitsunepie.qassistant"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "0.0.1"
        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a"))
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    androidResources {
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x64")
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    sourceSets.debug {
        kotlin.srcDir("build/generated/ksp/debug/kotlin")
    }
    sourceSets.release {
        kotlin.srcDir("build/generated/ksp/release/kotlin")
    }
}

dependencies {
    val composeVersion: String by project

    compileOnly("de.robv.android.xposed:api:82")
    implementation("com.tencent:mmkv-static:1.2.11")
    implementation("com.github.kyuubiran:EzXHelper:0.5.0")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")

    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    ksp(project(":compiler"))
}
