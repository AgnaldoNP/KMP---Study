package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.ui.text.AnnotatedString

expect fun String.format(vararg args: Any): String
expect fun String.parseHtml(): AnnotatedString
