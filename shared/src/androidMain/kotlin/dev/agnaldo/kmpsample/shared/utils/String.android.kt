package dev.agnaldo.kmpsample.shared.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle

actual fun String.format(vararg args: Any): String {
    return String.format(this, *args)
}

@OptIn(ExperimentalTextApi::class)
@SuppressLint("ObsoleteSdkInt")
actual fun String.parseHtml(): AnnotatedString {
    val spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }

    return buildAnnotatedString {
        var currentIndex = 0

        while (currentIndex < spanned.length) {
            val nextSpanStart = spanned.nextSpanTransition(currentIndex, spanned.length, Any::class.java)
            val text = spanned.subSequence(currentIndex, nextSpanStart).toString()
            val spans = spanned.getSpans(currentIndex, nextSpanStart, Any::class.java)

            if (spans.isEmpty()) {
                append(text)
            } else {
                spans.forEach { span ->
                    when (span) {
                        is android.text.style.StyleSpan -> {
                            if (span.style == android.graphics.Typeface.BOLD_ITALIC) {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Italic
                                    )
                                ) { append(text) }
                            }
                            if (span.style == android.graphics.Typeface.BOLD) {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(text)
                                }
                            }
                            if (span.style == android.graphics.Typeface.ITALIC) {
                                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                    append(text)
                                }
                            }
                        }
                        is URLSpan -> {
                            withAnnotation(UrlAnnotation(span.url)) {
                                append(text)
                            }
                        }
                        else -> append(text)
                    }
                }
            }
            currentIndex = nextSpanStart
        }
    }
}
