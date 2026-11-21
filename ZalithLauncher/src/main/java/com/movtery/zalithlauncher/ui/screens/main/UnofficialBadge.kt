/*
 * MCF Launcher is an unofficial modified version of Zalith Launcher 2
 * (Copyright (C) 2025-2026 MovTery and contributors), licensed under GPL-3.0.
 */

package com.movtery.zalithlauncher.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

/**
 * Обязательная по лицензии пометка: это неофициальная модифицированная версия.
 */
@Composable
fun UnofficialBadge(modifier: Modifier = Modifier) {
    val text = if (Locale.getDefault().language == "ru") {
        "MCF Launcher · неофициальная модифицированная версия"
    } else {
        "MCF Launcher · unofficial modified version"
    }
    Text(
        text = text,
        modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp),
        style = MaterialTheme.typography.labelSmall,
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
