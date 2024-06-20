package dev.agnaldo.kmpsample.designsystem.borders

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
val small: CornerBasedShape = RoundedCornerShape(4.dp),
    /**
 * Shape used by medium components like [Card] or [AlertDialog].
 */
    val medium: CornerBasedShape = RoundedCornerShape(4.dp),
    /**
 * Shape used by large components like [ModalDrawer] or [ModalBottomSheetLayout].
 */
    val large: CornerBasedShape = RoundedCornerShape(0.dp)
 */

class AppBorderStroke(
    small: BorderStroke,
    medium: BorderStroke,
    large: BorderStroke,
) {
    var small by mutableStateOf(small, structuralEqualityPolicy())
        internal set

    var medium by mutableStateOf(medium, structuralEqualityPolicy())
        internal set

    var large by mutableStateOf(large, structuralEqualityPolicy())
        internal set

    internal fun copy(
        small: BorderStroke = this.small,
        medium: BorderStroke = this.medium,
        large: BorderStroke = this.large,
    ): AppBorderStroke = AppBorderStroke(
        small = small,
        medium = medium,
        large = large
    )

    internal fun updateBordersFrom(other: AppBorderStroke) {
        small = other.small
        medium = other.medium
        large = other.large
    }

    companion object {
        internal val default = AppBorderStroke(
            small = BorderStroke(1.dp, Color.Black),
            medium = BorderStroke(2.dp, Color.Black),
            large = BorderStroke(4.dp, Color.Black)
        )
    }
}
