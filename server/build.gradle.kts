plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "dev.agnaldo.kmpsample"
version = "1.0.0"
application {
    mainClass.set("dev.agnaldo.kmpsample.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

sourceSets {
    val variant = BuildSrcConfig.Variant.getVariant(project = project, gradle = gradle)
    main {
        resources.srcDir("src/${variant.flavor.flavorName}/resources")
        resources.srcDir("src/${variant.variantName}/resources")
        kotlin.srcDir("src/${variant.flavor.flavorName}/kotlin")
        kotlin.srcDir("src/${variant.variantName}/kotlin")
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}
