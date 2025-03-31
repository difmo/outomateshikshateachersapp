plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.saptrishi.outomateshikshateachersapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saptrishi.outomateshikshateachersapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.circleimageview)
    implementation(libs.android.gif.drawable)
    implementation(libs.expandabletextview)
    implementation(libs.picasso)
    implementation(libs.volley)
    implementation(libs.glide)
    implementation (libs.firebase.messaging)
    implementation (libs.jsoup)
    implementation (libs.app.update)
    implementation (libs.caldroid)
}