package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.format(vararg args: Any): String {
    val format = if (args.isEmpty()) this else this.replace("%s", "%@")
    return when (args.count()) {
        0 -> return format
        1 -> return NSString.stringWithFormat(format, args[0])
        2 -> return NSString.stringWithFormat(format, args[0], args[1])
        3 -> return NSString.stringWithFormat(format, args[0], args[1], args[2])
        4 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3])
        5 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4])
        6 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5])
        7 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6])
        8 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7])
        9 -> return NSString.stringWithFormat(format, args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8])
        else -> format
    }
}

@OptIn(ExperimentalTextApi::class)
actual fun String.parseHtml(): AnnotatedString {
    return buildAnnotatedString {
        println(this@parseHtml)
        splitHtml(this@parseHtml).forEach {
            println("${it.text} ${it.modifiers}")
            if (it.modifiers.isNotEmpty()) {
                print("Eu aqui")
                when {
                    it.modifiers.contains("b") && it.modifiers.contains("i") -> withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) { append(it.text) }
                    it.modifiers.contains("b") -> withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(it.text) }
                    it.modifiers.contains("i") -> withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ) { append(it.text) }
                    it.modifiers.any { m -> m.contains("a ") } -> {
                        val url = it.modifiers.first { m -> m.contains("a ") }
                            .split(" ").firstOrNull { n ->
                                n.contains("href", true)
                            }?.split("=")?.get(1)
                            ?.removeSurrounding("\"").orEmpty()
                        withAnnotation(UrlAnnotation(url)) {
                            append(it.text)
                        }
                    }
                    else -> append(it.text)
                }
            } else {
                append(it.text)
            }
        }
    }
}

private data class AttributedHtmlString(
    val text: String,
    val modifiers: List<String> = emptyList()
)

private fun splitHtml(html: String): List<AttributedHtmlString> {
    if (!html.contains('<')) return listOf(AttributedHtmlString(html))
    val result = mutableListOf<AttributedHtmlString>()

    // check if for each '<' there is a '>', in sequence
    val openCloseChar = mutableListOf<String>()
    html.forEach { c ->
        if (c == '<') {
            openCloseChar.add("<")
        } else if (c == '>') {
            if (openCloseChar.isEmpty()) return listOf(AttributedHtmlString(html))

            val lastChar = openCloseChar.removeLast()
            if (lastChar != "<") return listOf(AttributedHtmlString(html))
        }
    }

    var inTag = false
    var isOpenTag = false
    var inCloseTag = false
    var currentTag = ""
    var currentContent = ""
    val stackTags = mutableListOf<String>()

    html.forEach { c ->
        if (c == '<') {
            inTag = true
            isOpenTag = true
            inCloseTag = false

            if (currentContent.isNotEmpty()) {
                result.add(AttributedHtmlString(currentContent, stackTags.toList()))
                currentContent = ""
            }
        } else if (c == '/' && inTag) {
            inCloseTag = true
        } else if (c == '>') {
            inTag = false
            if (inCloseTag) {
                if (currentContent.isNotBlank()) {
                    result.add(AttributedHtmlString(currentContent, stackTags.toList()))
                    currentContent = ""
                }

                if (stackTags.isNotEmpty()) {
                    stackTags.removeLast()
                    currentTag = ""
                }
            } else {
                if (isOpenTag) {
                    stackTags.add(currentTag)
                    currentTag = ""
                }
            }
        } else {
            if (inTag) {
                currentTag += c
            } else {
                currentContent += c
            }
        }
    }

    if (currentContent.isNotBlank()) {
        result.add(AttributedHtmlString(currentContent))
        currentContent = ""
    }
    return result
}
