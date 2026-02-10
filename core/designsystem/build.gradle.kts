plugins {
    id("skeleton.android.library")
    id("skeleton.android.compose")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.designsystem"
}

dependencies {
    api(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)
    implementation(libs.coil.compose)
}
