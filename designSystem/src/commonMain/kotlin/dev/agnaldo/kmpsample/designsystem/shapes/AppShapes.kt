package dev.agnaldo.kmpsample.designsystem.shapes

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
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

class AppShapes(
    small: CornerBasedShape,
    medium: CornerBasedShape,
    large: CornerBasedShape,
) {
    var small by mutableStateOf(small, structuralEqualityPolicy())
        internal set

    var medium by mutableStateOf(medium, structuralEqualityPolicy())
        internal set

    var large by mutableStateOf(large, structuralEqualityPolicy())
        internal set

    internal fun copy(
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large,
    ): AppShapes = AppShapes(
        small = small,
        medium = medium,
        large = large
    )

    internal fun updateShapesFrom(other: AppShapes) {
        small = other.small
        medium = other.medium
        large = other.large
    }

    companion object {
        internal val default = AppShapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(16.dp),
        )
    }
}
