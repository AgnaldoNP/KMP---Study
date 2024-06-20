package dev.agnaldo.kmpsample.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
