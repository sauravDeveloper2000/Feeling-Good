package com.example.feelinggood.core.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(
    desiredSpace: Int
) {
    Spacer(modifier = Modifier.height(desiredSpace.dp))
}