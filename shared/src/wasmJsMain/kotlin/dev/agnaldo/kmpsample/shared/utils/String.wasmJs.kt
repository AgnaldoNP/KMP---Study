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
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.parsing.DOMParser

actual fun String.format(vararg args: Any): String {
    var result = this
    args.forEach { arg ->
        result = result.replaceFirst("%s", arg.toString())
    }
    return result
}

@OptIn(ExperimentalTextApi::class)
actual fun String.parseHtml(): AnnotatedString {
    val parser = DOMParser()
    val document: Document = parser.parseFromString(
        str = this,
        type = "text/html".toJsString()
    )

    return buildAnnotatedString {
        fun traverse(node: Node) {
            when (node.nodeType) {
                Node.TEXT_NODE -> append(node.nodeValue.orEmpty())
                Node.ELEMENT_NODE -> {
                    val element = node as Element
                    when (element.tagName.lowercase()) {
                        "b", "strong" -> withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            element.childNodes.asList().forEach { traverse(it) }
                        }
                        "i", "italic" -> withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            element.childNodes.asList().forEach { traverse(it) }
                        }
                        "a" -> {
                            val url = element.getAttribute("href")
                            withAnnotation(UrlAnnotation(url.orEmpty())) {
                                element.childNodes.asList().forEach { traverse(it) }
                            }
                        }
                        else -> element.childNodes.asList().forEach { traverse(it) }
                    }
                }
            }
        }

        document.body?.childNodes?.asList()?.forEach { traverse(it) }
    }
}

private fun NodeList.asList(): List<Node> = (0 until length).mapNotNull { item(it) }
