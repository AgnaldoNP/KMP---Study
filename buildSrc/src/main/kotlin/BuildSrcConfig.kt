object BuildSrcConfig {

    object Dimension {
        const val CLIENT = "client"
    }

    enum class Flavor(
        val flavorName: String,
        val packageName: String,
        val androidAppName: String,
        val iosAppName: String
    ) {
        FLAVOR1(
            flavorName = "flavor1",
            packageName = "dev.agnaldo.kmpsample.flavor1",
            androidAppName = "Flavor 1",
            iosAppName = "Flavor 1"
        ),
        FLAVOR2(
            flavorName = "flavor2",
            packageName = "dev.agnaldo.kmpsample.flavor2",
            androidAppName = "Flavor 2",
            iosAppName = "Flavor 2"
        );

        companion object {
            fun fromName(name: String?): Flavor = values().firstOrNull { flavor ->
                flavor.flavorName.equals(
                    other = name,
                    ignoreCase = true
                )
            } ?: FLAVOR1

            /**
             * Get flavor from gradle running android (:composeApp:assembleFlavor1Debug)
             * or from properties (desktopRun ... -Ptarget=flavor1)
             */
            fun getFlavor(
                project: org.gradle.api.Project?,
                gradle: org.gradle.api.invocation.Gradle?
            ): Flavor {
                val properties = project?.properties.orEmpty()
                val buildTypes = SrcBuildType.values().map { it.buildTypeName }
                var target: String? = null

                val taskNames = gradle?.startParameter?.taskRequests
                    ?.flatMap { tr -> tr.args.map { it.toString() } }.orEmpty()

                val composeAndroidTaskStr = ":composeApp:assembleAndroid"
                val task = taskNames.firstOrNull { it.contains(composeAndroidTaskStr) }
                if (task != null) {
                    val buildType = buildTypes.firstOrNull { task.contains(other = it, ignoreCase = true) }
                    if (buildType != null) {
                        target = task.replace(oldValue = buildType, newValue = "", ignoreCase = true)
                            .replace(oldValue = composeAndroidTaskStr, newValue = "", ignoreCase = true)
                    }
                }
                if (target == null) {
                    target = properties["target"] as String?
                }
                return Flavor.fromName(target)
            }
        }
    }

    private const val IOS_SIMULATOR_DEVICE = "iPhone 15 Pro"

    enum class SrcBuildType(
        val buildTypeName: String,
        val iosConfiguration: String,
        val packageSuffix: String,
        val appNameSuffix: String,
        val isDebuggable: Boolean,
        val isMinifyEnabled: Boolean
    ) {
        DEBUG(
            buildTypeName = "debug",
            iosConfiguration = "Debug",
            packageSuffix = ".debug",
            appNameSuffix = " - DBG",
            isDebuggable = true,
            isMinifyEnabled = false
        ),
        HOMOLOG(
            buildTypeName = "homolog",
            iosConfiguration = "Homolog",
            packageSuffix = ".homolog",
            appNameSuffix = " - HML",
            isDebuggable = true,
            isMinifyEnabled = false
        ),
        RELEASE(
            buildTypeName = "release",
            iosConfiguration = "Release",
            packageSuffix = "",
            appNameSuffix = "",
            isDebuggable = false,
            isMinifyEnabled = true
        );

        companion object {
            fun fromName(name: String?): SrcBuildType = values().firstOrNull { buildType ->
                buildType.buildTypeName.equals(
                    other = name,
                    ignoreCase = true
                )
            } ?: DEBUG

            /**
             * Get variant from gradle running android (:composeApp:assembleFlavor1Debug)
             * or from properties (desktopRun ... -Pvariant=debug)
             */
            fun getBuildType(
                project: org.gradle.api.Project?,
                gradle: org.gradle.api.invocation.Gradle?
            ): SrcBuildType {
                val properties = project?.properties.orEmpty()
                val buildTypes = SrcBuildType.values().map { it.buildTypeName }
                var buildType: String? = null

                val taskNames = gradle?.startParameter?.taskRequests
                    ?.flatMap { tr -> tr.args.map { it.toString() } }.orEmpty()

                val composeAndroidTaskStr = ":composeApp:assemble"
                val task = taskNames.firstOrNull { it.contains(composeAndroidTaskStr) }
                if (task != null) {
                    buildType = buildTypes.firstOrNull { task.contains(other = it, ignoreCase = true) }
                }
                if (buildType == null) {
                    buildType = properties["variant"] as String?
                }
                return SrcBuildType.fromName(buildType)
            }
        }
    }

    enum class Variant(
        val variantName: String,
        val flavor: Flavor,
        val buildType: SrcBuildType,
        val packageName: String, // "dev.agnaldo.kmpsample.flavor2.debug"

        val androidAppName: String, // android launcher app name

        val signingName: String,
        val signingKeyPath: String?,
        val signingStorePassword: String?,
        val signingKeyAlias: String?,
        val signingKeyPassword: String?,

        val iosScheme: String, // flavor1Debug
        val iosAppName: String, // Flavor 1 - DBG.app
        val iosSimulatorDevice: String // iPhone 15 Pro
    ) {
        FLAVOR1_DEBUG(
            variantName = "flavor1Debug",
            flavor = Flavor.FLAVOR1,
            buildType = SrcBuildType.DEBUG,
            packageName = Flavor.FLAVOR1.packageName + SrcBuildType.DEBUG.packageSuffix,

            androidAppName = Flavor.FLAVOR1.androidAppName + SrcBuildType.DEBUG.appNameSuffix,

            signingName = "debug",
            signingKeyPath = null,
            signingStorePassword = null,
            signingKeyAlias = null,
            signingKeyPassword = null,

            iosScheme = Flavor.FLAVOR1.flavorName + SrcBuildType.DEBUG.iosConfiguration,
            iosAppName = Flavor.FLAVOR1.iosAppName + SrcBuildType.DEBUG.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        ),
        FLAVOR1_HOMOLOG(
            variantName = "flavor1Homolog",
            flavor = Flavor.FLAVOR1,
            buildType = SrcBuildType.HOMOLOG,
            packageName = Flavor.FLAVOR1.packageName + SrcBuildType.HOMOLOG.packageSuffix,

            androidAppName = Flavor.FLAVOR1.androidAppName + SrcBuildType.HOMOLOG.appNameSuffix,

            signingName = "flavor1Homolog",
            signingKeyPath = "../signing/keystore.keystore",
            signingStorePassword = "password",
            signingKeyAlias = "key",
            signingKeyPassword = "password",

            iosScheme = Flavor.FLAVOR1.flavorName + SrcBuildType.HOMOLOG.iosConfiguration,
            iosAppName = Flavor.FLAVOR1.iosAppName + SrcBuildType.HOMOLOG.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        ),
        FLAVOR1_RELEASE(
            variantName = "flavor1Release",
            flavor = Flavor.FLAVOR1,
            buildType = SrcBuildType.RELEASE,
            packageName = Flavor.FLAVOR1.packageName + SrcBuildType.HOMOLOG.packageSuffix,

            androidAppName = Flavor.FLAVOR1.androidAppName + SrcBuildType.RELEASE.appNameSuffix,

            signingName = "flavor1Release",
            signingKeyPath = "../signing/keystore.keystore",
            signingStorePassword = "password",
            signingKeyAlias = "key",
            signingKeyPassword = "password",

            iosScheme = Flavor.FLAVOR1.flavorName + SrcBuildType.RELEASE.iosConfiguration,
            iosAppName = Flavor.FLAVOR1.iosAppName + SrcBuildType.RELEASE.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        ),
        FLAVOR2_DEBUG(
            variantName = "flavor2Debug",
            flavor = Flavor.FLAVOR2,
            buildType = SrcBuildType.DEBUG,
            packageName = Flavor.FLAVOR2.packageName + SrcBuildType.DEBUG.packageSuffix,

            androidAppName = Flavor.FLAVOR2.androidAppName + SrcBuildType.DEBUG.appNameSuffix,

            signingName = "debug",
            signingKeyPath = null,
            signingStorePassword = null,
            signingKeyAlias = null,
            signingKeyPassword = null,

            iosScheme = Flavor.FLAVOR2.flavorName + SrcBuildType.DEBUG.iosConfiguration,
            iosAppName = Flavor.FLAVOR2.iosAppName + SrcBuildType.DEBUG.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        ),
        FLAVOR2_HOMOLOG(
            variantName = "flavor2Homolog",
            flavor = Flavor.FLAVOR2,
            buildType = SrcBuildType.HOMOLOG,
            packageName = Flavor.FLAVOR2.packageName + SrcBuildType.HOMOLOG.packageSuffix,

            androidAppName = Flavor.FLAVOR2.androidAppName + SrcBuildType.HOMOLOG.appNameSuffix,

            signingName = "flavor2Homolog",
            signingKeyPath = "../signing/keystore.keystore",
            signingStorePassword = "password",
            signingKeyAlias = "key",
            signingKeyPassword = "password",

            iosScheme = Flavor.FLAVOR2.flavorName + SrcBuildType.HOMOLOG.iosConfiguration,
            iosAppName = Flavor.FLAVOR2.iosAppName + SrcBuildType.HOMOLOG.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        ),
        FLAVOR2_RELEASE(
            variantName = "flavor2Release",
            flavor = Flavor.FLAVOR2,
            buildType = SrcBuildType.RELEASE,
            packageName = Flavor.FLAVOR2.packageName + SrcBuildType.HOMOLOG.packageSuffix,

            androidAppName = Flavor.FLAVOR2.androidAppName + SrcBuildType.RELEASE.appNameSuffix,

            signingName = "flavor2Release",
            signingKeyPath = "../signing/keystore.keystore",
            signingStorePassword = "password",
            signingKeyAlias = "key",
            signingKeyPassword = "password",

            iosScheme = Flavor.FLAVOR2.flavorName + SrcBuildType.RELEASE.iosConfiguration,
            iosAppName = Flavor.FLAVOR2.iosAppName + SrcBuildType.RELEASE.appNameSuffix + ".app",
            iosSimulatorDevice = IOS_SIMULATOR_DEVICE
        );

        companion object {
            fun fromName(variantName: String?): Variant =
                values().firstOrNull { variant ->
                    variant.variantName.equals(
                        other = variantName,
                        ignoreCase = true
                    )
                } ?: FLAVOR1_DEBUG

            fun getVariant(
                project: org.gradle.api.Project?,
                gradle: org.gradle.api.invocation.Gradle?
            ): Variant {
                val flavor = Flavor.getFlavor(project, gradle)
                val buildType = SrcBuildType.getBuildType(project, gradle)
                return Variant.fromName(
                    variantName = flavor.flavorName + buildType.buildTypeName
                )
            }
        }
    }
}
