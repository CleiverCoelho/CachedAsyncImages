plugins {
    id("skeleton.android.library")
    id("skeleton.android.hilt")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.datastore)

    implementation(libs.kotlinx.coroutines.android)
}
