/*
 * QAssistant - An Xposed module for QQ/TIM
 * Copyright (C) 2021-2022
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
pluginManagement {
    val kspVersion: String by settings
    val kotlinVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://api.xposed.info/")
        maven("https://jitpack.io")
    }
}

include(":app", ":compiler")
rootProject.name = "QAssistant"
val compilerLibsDir = File(settingsDir, "libs")
project(":compiler").projectDir = File(compilerLibsDir, "compiler")
