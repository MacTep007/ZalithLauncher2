/*
 * MCF Launcher is an unofficial modified version of Zalith Launcher 2
 * (Copyright (C) 2025-2026 MovTery and contributors), licensed under GPL-3.0.
 */

package com.movtery.zalithlauncher.ui.screens.content.download.addon.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.game.download.addon.platform.Platform
import com.movtery.zalithlauncher.game.download.addon.platform.PlatformDisplayLabel
import com.movtery.zalithlauncher.utils.formatNumberByLocale
import java.util.Locale

/**
 * Плиточная карточка мода в стиле магазина приложений:
 * сверху «баннер» с иконкой, названием, автором и загрузками, снизу описание, загрузчики и кнопка.
 */
@Composable
fun ResultProjectCard(
    modifier: Modifier = Modifier,
    platform: Platform,
    title: String,
    description: String,
    iconUrl: String? = null,
    authors: List<String>? = null,
    downloads: Long = 0L,
    modloaders: List<PlatformDisplayLabel>? = null,
    isInstalled: Boolean = false,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val soft = Color(0xFFE0E0E0)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = MaterialTheme.colorScheme.onSurface,
        onClick = onClick
    ) {
        Column {
            //верхняя часть: размытая иконка как фон + затемнение
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clipToBounds()
            ) {
                AddonIcon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .blur(24.dp),
                    size = 280.dp,
                    iconUrl = iconUrl
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0x40000000), Color(0xCC000000))
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    PlatformIdentifier(platform = platform)
                }
                if (isInstalled) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        InstalledModBadge()
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AddonIcon(
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                        size = 48.dp,
                        iconUrl = iconUrl
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            authors?.takeIf { it.isNotEmpty() }?.let {
                                Text(
                                    modifier = Modifier.weight(1f, fill = false),
                                    text = it.joinToString(", "),
                                    color = soft,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(R.drawable.ic_download_2_filled),
                                contentDescription = null,
                                tint = soft
                            )
                            Text(
                                text = formatNumberByLocale(context, downloads),
                                color = soft,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            //нижняя часть: описание, загрузчики, кнопка
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        modloaders?.take(3)?.forEach { loader ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHighest
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    text = loader.getDisplayName(),
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                    FilledTonalButton(
                        onClick = onClick,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text(text = if (Locale.getDefault().language == "ru") "Подробнее" else "Details")
                    }
                }
            }
        }
    }
}
