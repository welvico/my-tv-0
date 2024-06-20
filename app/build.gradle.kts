import java.io.BufferedReader

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.lizongying.mytv0'
    compileSdk 34

    viewBinding {
        enabled = true
    }

    defaultConfig {
        applicationId "com.lizongying.mytv0"
        minSdk 17
        targetSdk 33
        versionCode VersionCode()
        versionName VersionName()

        // This block is different from the one you use to link Gradle
        // to your CMake or ndk-build script.
        externalNativeBuild {

            // For ndk-build, instead use the ndkBuild block.
            cmake {
                arguments "-DIS_SO_BUILD=${project.hasProperty('IS_SO_BUILD') ? project.IS_SO_BUILD : true}"

//                abiFilters "armeabi-v7a", "arm64-v8a", "x86", "x86_64"
                abiFilters "armeabi-v7a", "arm64-v8a"
            }
        }

        // Similar to other properties in the defaultConfig block,
        // you can configure the ndk block for each product flavor
        // in your build configuration.
        ndk {
            // Specifies the ABI configurations of your native
            // libraries Gradle should build and package with your app.
            abiFilters "armeabi-v7a", "arm64-v8a"
        }
        multiDexEnabled true
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = 17
    }

    // Encapsulates your external native build configurations.
    externalNativeBuild {

        // Encapsulates your CMake build configurations.
        cmake {

            // Provides a relative path to your CMake build script.
            path = file("CMakeLists.txt")
        }
    }
}

static def VersionCode() {
    try {
        def p = "git describe --tags --always"
        def process = p.execute()
        process.waitFor()
        def replace = [v: "", ".": " ", "-": " "]
        def arr = (process.text.trim().replace(replace) + " 0").split(" ")
        def versionCode = arr[0].toInteger() * 16777216 + arr[1].toInteger() * 65536 + arr[2].toInteger() * 256 + arr[3].toInteger()
        return versionCode
    } catch (ignored) {
        return 1
    }
}

static def VersionName() {
    try {
        def process = "git describe --tags --always".execute()
        process.waitFor()
        def versionName = process.text.trim() - "v"
        if (versionName.isEmpty()) {
            versionName = "1.0.0"
        }
        return versionName
    } catch (ignored) {
        return "1.0.0"
    }
}

dependencies {
    implementation(libs.media3.ui)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.protobuf.kotlin)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.converter.protobuf)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.work.runtime.ktx)
    implementation(libs.core.ktx)
    implementation(libs.multidex)
    implementation(libs.leanback)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.serialization.json)
    implementation(libs.coroutines.android)
    implementation(libs.javax.annotation.api)
}
