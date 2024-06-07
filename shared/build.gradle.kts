import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
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
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
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
            dependencies { /* put your Multiplatform dependencies here */ }
            kotlin.srcDir("src/common${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/common${variant.variantName.capitalized()}/kotlin")
        }

        wasmJsMain.apply {
            dependencies { /* put your Multiplatform dependencies here */ }
            kotlin.srcDir("src/wasmJsMain${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/wasmJsMain${variant.variantName.capitalized()}/kotlin")
        }

        iosMain {
            dependencies { /* put your Multiplatform dependencies here */ }
            kotlin.srcDir("src/ios${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/ios${variant.variantName.capitalized()}/kotlin")
        }

        desktopMain.apply {
            dependencies { /* put your Multiplatform dependencies here */ }
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
            create("android" + flavor.flavorName.capitalized()) {
                dimension = BuildSrcConfig.Dimension.CLIENT
            }
        }
    }
}
