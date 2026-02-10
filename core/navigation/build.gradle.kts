plugins {
    id("skeleton.android.library")
    id("skeleton.android.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cleivercoelho.skeleton.core.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
