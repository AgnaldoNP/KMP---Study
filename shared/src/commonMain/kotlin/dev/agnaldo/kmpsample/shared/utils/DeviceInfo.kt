package dev.agnaldo.kmpsample.shared.utils

import androidx.compose.runtime.Composable

@Composable
expect fun getScreenSize(): Pair<Int, Int>
