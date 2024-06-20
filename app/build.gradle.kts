import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android") version "1.6.10"
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.lizongying.mytv0"
        minSdk = 19
        targetSdk = 32
        versionCode = getVersionCode()
        versionName = getVersionName()
        multiDexEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    dependencies {
        implementation("androidx.core:core-ktx:1.7.0")
        implementation("androidx.appcompat:appcompat:1.4.1")
        implementation("androidx.multidex:multidex:2.0.1")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        
        // media3 dependencies
        val media3Version = "1.3.1"
        implementation("androidx.media3:media3-ui:$media3Version")
        implementation("androidx.media3:media3-exoplayer:$media3Version")
        implementation("androidx.media3:media3-exoplayer-hls:$media3Version")
        implementation("androidx.media3:media3-exoplayer-dash:$media3Version")
        implementation("androidx.media3:media3-exoplayer-rtsp:$media3Version")
        
        // Other dependencies
        implementation("com.github.bumptech.glide:glide:4.16.0")
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.google.android.exoplayer:exoplayer-ui:2.13.3")
        implementation("com.google.android.exoplayer:exoplayer-core:2.13.3")
        implementation("com.google.android.exoplayer:exoplayer-hls:2.13.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
        implementation(files("libs/lib-decoder-ffmpeg-release.aar"))
        implementation("io.github.lizongying:gua64:1.4.5")
        implementation("org.nanohttpd:nanohttpd:2.3.1")
        implementation("com.google.zxing:core:3.5.3")
        implementation("androidx.leanback:leanback:1.0.0")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

fun getVersionCode(): Int {
    return try {
        val process = Runtime.getRuntime().exec("git describe --tags --always")
        process.waitFor()
        val arr = (process.inputStream.bufferedReader().use(BufferedReader::readText).trim()
            .replace("v", "").replace(".", " ").replace("-", " ") + " 0").split(" ")
        val versionCode =
            arr[0].toInt() * 16777216 + arr[1].toInt() * 65536 + arr[2].toInt() * 256 + arr[3].toInt()
        versionCode
    } catch (ignored: Exception) {
        1
    }
}

fun getVersionName(): String {
    return try {
        val process = Runtime.getRuntime().exec("git describe --tags --always")
        process.waitFor()
        val versionName = process.inputStream.bufferedReader().use(BufferedReader::readText).trim().removePrefix("v")
        versionName.ifEmpty {
            "1.0.0"
        }
    } catch (ignored: Exception) {
        "1.0.0"
    }
}
