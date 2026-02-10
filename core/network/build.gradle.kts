plugins {
    id("skeleton.android.library")
    id("skeleton.android.hilt")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.datastore)

    api(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.coroutines.android)
}
