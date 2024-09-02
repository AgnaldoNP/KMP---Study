import com.android.build.api.variant.ResValue
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
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
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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

        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.kotlinx.coroutines.core)

                implementation(projects.designSystem)
                implementation(projects.shared)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.androidx.navigation.compose)

                implementation(libs.compottie)
                implementation(libs.koin.core)
            }

            kotlin.srcDir("src/common${variant.flavor.flavorName.capitalized()}/kotlin")
            resources.srcDir("src/common${variant.flavor.flavorName.capitalized()}/composeResources")
            kotlin.srcDir("src/common${variant.variantName.capitalized()}/kotlin")
            resources.srcDir("src/common${variant.variantName.capitalized()}/composeResources")
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.accompanist.permissions)
            implementation(libs.koin.android)
        }

        desktopMain.apply {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.koin.core)

                implementation(libs.webcam.capture)
                implementation(libs.webcam.capture.driver.native)
            }

            kotlin.srcDir("src/desktop${variant.flavor.flavorName.capitalized()}/kotlin")
            resources.srcDir("src/desktop${variant.flavor.flavorName.capitalized()}/composeResources")
            kotlin.srcDir("src/desktop${variant.variantName.capitalized()}/kotlin")
            resources.srcDir("src/desktop${variant.variantName.capitalized()}/composeResources")
        }

        wasmJsMain.apply {
            kotlin.srcDir("src/wasmJs${variant.flavor.flavorName.capitalized()}/kotlin")
            kotlin.srcDir("src/wasmJs${variant.variantName.capitalized()}/kotlin")
        }

        iosMain {
            dependencies {
                implementation(libs.koin.core)
            }
        }
    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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
            val name = "android" + flavor.flavorName.capitalized()
            (findByName(name) ?: create(name)).apply {
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
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        debugImplementation(libs.compose.ui.tooling.preview)
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

fun listPhysicalDevices(): List<String> {
    val listDevicesCommand = "xcrun xctrace list devices"
    val commandArgs = listDevicesCommand.split(" ").toTypedArray()
    val devicesReturn = runCommandAndWaitResult(*commandArgs)
    if (devicesReturn.first != 0) throw Exception("Failed to list devices")
    val lines = devicesReturn.second.lines()
    val start = lines.indexOfFirst { it.contains("== Devices ==") }
    val end = lines.indexOfFirst { it.isEmpty() }
    val devices = lines.subList(start + 1, end).filter { it.contains("iPhone") }
    println("  Filtered Devices:\n    ${devices.joinToString("\n    ")}")
    return devices
}

tasks.register("buildIos") {
    group = "application"
    description =
        "Build the iOS application. Use -Ptarget and -Pvariant to specify the product you want to build."
    val projectDir = project.projectDir.parentFile.absolutePath

    doLast {
        val xcodeProjectFile = File(projectDir, "iosApp/iosApp.xcodeproj")
        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)
        val devices = listPhysicalDevices()

        val buildResult = if (devices.isNotEmpty()) {
            val buildDestination = File(projectDir, "build/ios")
            val device = devices.first()
            val deviceId = device.split(" ").last().replace("(", "")
                .replace(")", "").replace(")", "")
            runCommandAndWaitResult(
                "xcodebuild",
                "-project",
                xcodeProjectFile.absolutePath,
                "-scheme",
                variant.iosScheme,
                "-configuration",
                variant.buildType.iosConfiguration,
                "OBJROOT=${buildDestination.absolutePath}",
                "SYMROOT=${buildDestination.absolutePath}",
                "-destination",
                "id=$deviceId",
                "-allowProvisioningDeviceRegistration",
                "-allowProvisioningUpdates",
                "-derivedDataPath",
                "build"
            )
        } else {
            val buildDestination = File(projectDir, "build/ios/Debug-iphonesimulator")
            runCommandAndWaitResult(
                "xcodebuild",
                "-project",
                xcodeProjectFile.absolutePath,
                "-scheme",
                variant.iosScheme,
                "-configuration",
                variant.buildType.iosConfiguration,
                "OBJROOT=${buildDestination.absolutePath}",
                "SYMROOT=${buildDestination.absolutePath}",
                "-destination",
                "platform=iOS Simulator,name=${variant.iosSimulatorDevice},OS=latest",
                "-allowProvisioningDeviceRegistration",
                "-allowProvisioningUpdates",
                "-derivedDataPath",
                "build"
            )
        }
        if (buildResult.first != 0) throw Exception("iOS Build failed with exit code ${buildResult.first}")
    }
}

tasks.register("runIos") {
    group = "application"
    description =
        "Run the iOS application. Use -Ptarget and -Pvariant to specify the product you want to build."
    val projectDir = project.projectDir.parentFile.absolutePath

    doLast {
        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)

        val devices = listPhysicalDevices()
        if (devices.isNotEmpty()) {
            val buildDestination = File(projectDir, "build/ios")
            val device = devices.first()
            val deviceId = device.split(" ").last().replace("(", "")
                .replace(")", "").replace(")", "")

            val appDir = File(buildDestination, variant.iosAppName)

            // Install and launch the app on the device
            val installResult = runCommandAndWaitResult(
                "ideviceinstaller",
                "-i",
                appDir.absolutePath,
                "-u",
                deviceId
            )
            if (installResult.first != 0) throw Exception("Failed to install the app on the device $device")

            // val launchResult = runCommandAndWaitResult("idevicedebug", "-u", deviceId, "run", variant.packageName)
            // if (launchResult.first != 0) throw Exception("Failed to launch the app on the device $device")
        } else {
            val buildDestination = File(projectDir, "build/ios/Debug-iphonesimulator")

            // Starts the simulator and waits for it to boot up
            var startSimulatorResult = runCommandAndWaitResult("open", "-a", "Simulator")
            if (startSimulatorResult.first != 0) throw Exception("Failed to start the simulator")

            val bootStatusResult =
                runCommandAndWaitResult("xcrun", "simctl", "bootstatus", "booted", "-b")
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
                val bootResult =
                    runCommandAndWaitResult("xcrun", "simctl", "boot", variant.iosSimulatorDevice)
                if (bootResult.first != 0) throw Exception("Failed to boot the simulator")

                val bootDeviceStatusResult = runCommandAndWaitResult(
                    "xcrun",
                    "simctl",
                    "bootstatus",
                    variant.iosSimulatorDevice,
                    "-b"
                )
                if (bootDeviceStatusResult.first != 0) {
                    throw Exception("Failed to check the simulator ${variant.iosSimulatorDevice} boot status")
                }
            }

            val appDir1 = File(buildDestination, "Debug-iphonesimulator/${variant.iosAppName}")
            val appDir2 = File(buildDestination, variant.iosAppName)
            val appDir = appDir1.takeIf { it.exists() } ?: appDir2

            // Install and launch the app on the device
            val installResult = runCommandAndWaitResult(
                "xcrun",
                "simctl",
                "install",
                variant.iosSimulatorDevice,
                appDir.absolutePath
            )
            if (installResult.first != 0) throw Exception("Failed to install the app on the simulator")

            val launchResult = runCommandAndWaitResult(
                "xcrun",
                "simctl",
                "launch",
                variant.iosSimulatorDevice,
                variant.packageName
            )
            if (launchResult.first != 0) throw Exception("Failed to launch the app on the simulator")
        }
    }
}

tasks.register("buildAndRunIos") {
    group = "application"
    description =
        "Build and run the iOS application. Use -Ptarget and -Pvariant to specify the product you want to build."

    dependsOn("buildIos")
    finalizedBy("runIos")
}

fun recursiveCopy(from: File, to: File) {
    if (!from.exists()) return
    from.listFiles()?.forEach { file ->
        if (file.name != ".DS_Store") {
            if (file.isDirectory) {
                val newDir = File(to, file.name)
                newDir.mkdir()
                recursiveCopy(file, newDir)
            } else {
                val destinationFile = File(to, file.name)
                if (destinationFile.exists()) {
                    println("Overwriting $destinationFile")
                    destinationFile.delete()
                }
                if (!destinationFile.parentFile.exists()) {
                    destinationFile.parentFile.mkdirs()
                }
                println("Copying $file to $to")
                file.copyTo(destinationFile)
            }
        }
    }
}

tasks.register("copyKmpResources") {
    outputs.upToDateWhen { false }
    doLast {
        println("\n\n--> Copying Flavored KMP Resources")

        val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)
        val taskNames =
            gradle.startParameter.taskRequests.flatMap { tr -> tr.args.map { it.toString() } }
        println("taskNames: ${taskNames.joinToString("\n")}?")

        val projectDir = project.projectDir.absolutePath
        val fromFlavor = File(
            projectDir,
            "src/common${variant.flavor.flavorName.capitalized()}/composeResources/"
        )
        val fromVariant = File(
            projectDir,
            "src/common${variant.variantName.capitalized()}/composeResources/"
        )

        val destination =
            "build/generated/compose/resourceGenerator/preparedResources/commonMain/composeResources/"

        println("destination:\n  $destination")
        val destinationFile = File(projectDir, destination)
        println(" - copying \"$fromFlavor\" to \"$destinationFile\"")
        recursiveCopy(fromFlavor, destinationFile)
        println(" - copying \"$fromVariant\" to \"$destinationFile\"")
        recursiveCopy(fromVariant, destinationFile)
    }
}

tasks.named("generateComposeResClass") {
    dependsOn("copyKmpResources")
}
dependencies {
    implementation(libs.volley)
    implementation(libs.material3.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.ui.android)
}
