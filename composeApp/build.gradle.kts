import com.android.build.api.variant.ResValue
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
        val wasmJsMain by getting
        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(projects.designSystem)
                implementation(projects.shared)
                implementation(libs.kotlinx.datetime)
            }
            kotlin.srcDir("src/common${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/common${variant.variantName.capitalized()}/kotlin")
        }
        desktopMain.apply {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
            kotlin.srcDir("src/desktop${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/desktop${variant.variantName.capitalized()}/kotlin")
        }

        wasmJsMain.apply {
            kotlin.srcDir("src/wasmJs${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/wasmJs${variant.variantName.capitalized()}/kotlin")
        }
    }
}

android {
    namespace = "dev.agnaldo.kmpsample"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        BuildSrcConfig.SrcBuildType.values().forEach { buildType ->
            (findByName(buildType.buildTypeName) ?: create(buildType.buildTypeName)).apply {
                isDebuggable = buildType.isDebuggable
                isMinifyEnabled = buildType.isMinifyEnabled
            }
        }
    }
    flavorDimensions += BuildSrcConfig.Dimension.CLIENT
    productFlavors {
        BuildSrcConfig.Flavor.values().forEach { flavor ->
            create("andriod" + flavor.flavorName.capitalized()) {
                dimension = BuildSrcConfig.Dimension.CLIENT
                applicationId = flavor.packageName
            }
        }
    }
    signingConfigs {
        BuildSrcConfig.Variant.values().forEach { variant ->
            if (variant.signingKeyPath != null) {
                create(variant.signingName) {
                    storeFile = file(variant.signingKeyPath.orEmpty())
                    storePassword = variant.signingStorePassword
                    keyAlias = variant.signingKeyAlias
                    keyPassword = variant.signingKeyPassword
                }
            }
        }
    }
    androidComponents {
        onVariants { gradleVariant ->
            val variant = BuildSrcConfig.Variant.fromName(variantName = gradleVariant.name)
            buildTypes {
                val buildTypeName = gradleVariant.buildType.orEmpty()
                (findByName(buildTypeName) ?: create(buildTypeName)).apply {
                    applicationIdSuffix = variant.buildType.packageSuffix
                    versionNameSuffix = variant.buildType.appNameSuffix
                    signingConfig = signingConfigs.getByName(variant.signingName)
                }
            }

            val resValueKey = object : ResValue.Key {
                override val name: String = "app_name"
                override val type: String = "string"
            }
            gradleVariant.resValues.apply {
                put(
                    resValueKey,
                    ResValue(variant.androidAppName, "app_name")
                )
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.agnaldo.kmpsample"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

fun runCommandAndWaitResult(vararg args: String): Pair<Int, String> {
    val command = args.joinToString(" ") {
        if (it.contains(" ")) "'$it\'" else it
    }
    println("\n> Running command: $command")
    val processBuilder = ProcessBuilder(*args).redirectErrorStream(true)
    val process = processBuilder.start()
    val successResult = StringBuilder()
    val errorResult = StringBuilder()
    process.inputStream.bufferedReader().forEachLine {
        successResult.appendLine(it)
        println("    $it")
    }
    process.errorStream.bufferedReader().forEachLine {
        errorResult.appendLine(it)
        println("    $it")
    }
    val exitCode = process.waitFor()
    val result = successResult.toString() + errorResult.toString()
    println("  Command exit code: $exitCode")
    return exitCode to result
}

fun runCommandAndWait(vararg args: String) {
    runCommandAndWaitResult(args = args)
}

fun waitUntil(timeout: Long = 5L, condition: () -> Boolean): Boolean {
    val startTime = System.currentTimeMillis()
    while (!condition()) {
        if (System.currentTimeMillis() - startTime >= timeout * 1000) {
            return false // Timeout reached
        }
        Thread.sleep(1000) // Wait for 1 second (This won't work within Gradle)
    }
    return true
}

fun isSimulatorRunning(device: String): Boolean {
    // List simulator Devices given the device identifier
    val devicesCheck = runCommandAndWaitResult("xcrun", "simctl", "list", "devices", device)
    // Check if the simulator is running based on a result like "iPhone 15 Pro (2E0....8F1) (Booted)"
    return devicesCheck.second.lines().any {
        val line = it.trim().replace("  ", " ")
        val identifier = "$device ("
        val bootedStr = "(Booted)"
        line.startsWith(identifier) && line.endsWith(bootedStr)
    }
}

tasks.register("buildAndRunIos") {
    group = "application"
    description = "Build and run the iOS application. Use -Ptarget and -Pvariant to specify the product you want to build."

    doLast {
        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)
        val buildResult = runCommandAndWaitResult(
            "xcodebuild",
            "-project",
            "iosApp/iosApp.xcodeproj",
            "-scheme",
            variant.iosScheme,
            "-configuration",
            variant.buildType.iosConfiguration,
            "OBJROOT=../build/ios/Debug-iphonesimulator",
            "SYMROOT=../build/ios/Debug-iphonesimulator",
            "-destination",
            "platform=iOS Simulator,name=${variant.iosSimulatorDevice},OS=latest",
            "-allowProvisioningDeviceRegistration",
            "-allowProvisioningUpdates",
            "-derivedDataPath",
            "build"
        )

        if (buildResult.first != 0) throw Exception("iOS Build failed with exit code ${buildResult.first}")

        // Starts the simulator and waits for it to boot up
        var startSimulatorResult = runCommandAndWaitResult("open", "-a", "Simulator")
        if (startSimulatorResult.first != 0) throw Exception("Failed to start the simulator")

        val bootStatusResult = runCommandAndWaitResult("xcrun", "simctl", "bootstatus", "booted", "-b")
        when (bootStatusResult.first) {
            0 -> {}
            148 -> {
                Thread.sleep(1000)
                runCommandAndWaitResult("killall", "Simulator")
                startSimulatorResult = runCommandAndWaitResult("open", "-a", "Simulator")
                if (startSimulatorResult.first != 0) throw Exception("Failed to start the simulator")
            }
            else -> throw Exception("Failed to check the simulator boot status")
        }

        waitUntil { isSimulatorRunning(variant.iosSimulatorDevice) }
        if (!isSimulatorRunning(variant.iosSimulatorDevice)) {
            val bootResult = runCommandAndWaitResult("xcrun", "simctl", "boot", variant.iosSimulatorDevice)
            if (bootResult.first != 0) throw Exception("Failed to boot the simulator")

            val bootDeviceStatusResult = runCommandAndWaitResult("xcrun", "simctl", "bootstatus", variant.iosSimulatorDevice, "-b")
            if (bootDeviceStatusResult.first != 0) {
                throw Exception("Failed to check the simulator ${variant.iosSimulatorDevice} boot status")
            }
        }

        // Install and launch the app on the device
        val installResult =
            runCommandAndWaitResult(
                "xcrun",
                "simctl",
                "install",
                variant.iosSimulatorDevice,
                "build/ios/Debug-iphonesimulator/${variant.iosAppName}"
            )
        if (installResult.first != 0) throw Exception("Failed to install the app on the simulator")

        val launchResult = runCommandAndWaitResult("xcrun", "simctl", "launch", variant.iosSimulatorDevice, variant.packageName)
        if (launchResult.first != 0) throw Exception("Failed to launch the app on the simulator")
    }
}
