plugins {
    id("skeleton.android.library")
    id("skeleton.android.hilt")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.datastore"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)
}
