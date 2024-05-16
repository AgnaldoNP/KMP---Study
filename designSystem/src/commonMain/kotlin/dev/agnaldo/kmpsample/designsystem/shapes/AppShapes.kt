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
    internal var small by mutableStateOf(small, structuralEqualityPolicy())
        internal set

    internal var medium by mutableStateOf(medium, structuralEqualityPolicy())
        internal set

    internal var large by mutableStateOf(large, structuralEqualityPolicy())

    internal fun copy(
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large,
    ): AppShapes = AppShapes(
        small = this.small,
        medium = this.medium,
        large = this.large
    )

    internal fun updateShapesFrom(other: AppShapes) {
        small = other.small
        medium = other.medium
        large = other.large
    }

    companion object {
        internal val default = AppShapes(
            small = RoundedCornerShape(1.dp),
            medium = RoundedCornerShape(2.dp),
            large = RoundedCornerShape(0.dp),
        )
    }
}
