@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.designsystem.widgets

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    linkColor: Color = AppTheme.colors.contrast,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onClick: ((Int) -> Unit)? = null
) {
    AppText(
        text = AnnotatedString(text),
        modifier = modifier,
        color = color,
        linkColor = linkColor,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style,
        onClick = onClick
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun AppText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    linkColor: Color = AppTheme.colors.contrast,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onClick: ((offset: Int) -> Unit)? = null,
    onLinkClick: ((link: String) -> Unit)? = null
) {
    if (onClick != null || onLinkClick != null) {
        ClickableText(
            text = buildAnnotatedString {
                fun appendWithDefaultStyle(appendText: AnnotatedString) {
                    withStyle(
                        style = SpanStyle(
                            color = color,
                            fontSize = fontSize,
                            fontStyle = fontStyle,
                            fontWeight = fontWeight,
                            fontFamily = fontFamily,
                            letterSpacing = letterSpacing,
                            textDecoration = textDecoration,
                        )
                    ) {
                        append(appendText)
                    }
                }

                @Composable
                fun appendWithUrlStyle(appendText: AnnotatedString) {
                    withStyle(
                        style = SpanStyle(
                            color = linkColor,
                            fontSize = fontSize,
                            fontStyle = fontStyle,
                            fontWeight = fontWeight,
                            fontFamily = fontFamily,
                            letterSpacing = letterSpacing,
                            textDecoration = textDecoration,
                        )
                    ) {
                        append(appendText)
                    }
                }

                val urlAnnotations = text.getUrlAnnotations(0, text.length)
                if (urlAnnotations.isNotEmpty()) {
                    var start = 0
                    var urlIndex = 0

                    do {
                        val url = urlAnnotations.getOrNull(urlIndex)
                        urlIndex++

                        if (url == null) {
                            append(text.subSequence(start, text.length))
                            break
                        } else {
                            val nonUrlStart = start
                            val nonUrlEnd = url.start
                            appendWithDefaultStyle(text.subSequence(nonUrlStart, nonUrlEnd))

                            val urlStart = url.start
                            val urlEnd = url.end
                            appendWithUrlStyle(text.subSequence(urlStart, urlEnd))

                            start = urlEnd
                        }
                    } while (start < text.length)
                } else {
                    appendWithDefaultStyle(text)
                }
            },
            modifier = modifier,
            style = style,
            softWrap = softWrap,
            overflow = overflow,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
            onClick = { offset ->
                if (onLinkClick != null) {
                    text.getUrlAnnotations(offset, offset).firstOrNull()?.let { range ->
                        onLinkClick.invoke(range.item.url)
                    }
                }
                onClick?.invoke(offset)
            }
        )
    } else {
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            inlineContent = inlineContent,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}
