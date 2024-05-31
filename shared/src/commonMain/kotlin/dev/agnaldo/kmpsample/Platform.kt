package dev.agnaldo.kmpsample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
