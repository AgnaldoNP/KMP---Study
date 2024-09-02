import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            binaries.executable()
            webpackTask { sourceMaps = true }
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        val wasmJsMain by getting

        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)
        commonMain {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.bundles.ktor.common)
                implementation(libs.kotlin.serializarion)
                implementation(libs.kotlinx.coroutines.core)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
            }
            kotlin.srcDir("src/common${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/common${variant.variantName.capitalized()}/kotlin")
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.activity.compose)
        }

        wasmJsMain.apply {
            dependencies {
                implementation(libs.ktor.client.wasm)
            }
            kotlin.srcDir("src/wasmJsMain${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/wasmJsMain${variant.variantName.capitalized()}/kotlin")
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
            kotlin.srcDir("src/ios${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/ios${variant.variantName.capitalized()}/kotlin")
        }

        desktopMain.apply {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.jsoup)
            }
            kotlin.srcDir("src/desktop${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/desktop${variant.variantName.capitalized()}/kotlin")
        }
    }
}

android {
    namespace = "dev.agnaldo.kmpsample.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildTypes {
        BuildSrcConfig.SrcBuildType.values().forEach { buildType ->
            (findByName(buildType.buildTypeName) ?: create(buildType.buildTypeName)).apply {
                isMinifyEnabled = buildType.isMinifyEnabled
            }
        }
    }
    flavorDimensions += BuildSrcConfig.Dimension.CLIENT
    productFlavors {
        BuildSrcConfig.Flavor.values().forEach { flavor ->
            val name = "android" + flavor.flavorName.capitalized()
            (findByName(name) ?: create(name)).apply {
                dimension = BuildSrcConfig.Dimension.CLIENT
            }
        }
    }
}
dependencies {
    implementation(libs.androidx.ui.text.android)
}
