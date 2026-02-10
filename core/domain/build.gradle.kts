plugins {
    id("skeleton.jvm.library")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}
