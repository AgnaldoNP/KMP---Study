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
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

actual fun String.format(vararg args: Any): String {
    return String.format(this, *args)
}

@OptIn(ExperimentalTextApi::class)
actual fun String.parseHtml(): AnnotatedString {
    val document = Jsoup.parse(this)
    return buildAnnotatedString {
        fun traverse(node: Node) {
            when (node) {
                is TextNode -> append(node.text())
                is Element -> {
                    when (node.tagName().lowercase()) {
                        "b", "strong" -> withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            node.childNodes().forEach { traverse(it) }
                        }
                        "i", "italic" -> withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            node.childNodes().forEach { traverse(it) }
                        }
                        "a" -> {
                            val url = node.attr("href")
                            withAnnotation(UrlAnnotation(url)) {
                                node.childNodes().forEach { traverse(it) }
                            }
                        }
                        else -> node.childNodes().forEach { traverse(it) }
                    }
                }
            }
        }

        document.body().childNodes().forEach { traverse(it) }
    }
}
