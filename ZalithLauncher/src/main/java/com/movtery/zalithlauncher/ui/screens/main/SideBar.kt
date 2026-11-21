/*
 * MCF Launcher is an unofficial modified version of Zalith Launcher 2
 * (Copyright (C) 2025-2026 MovTery and contributors), licensed under GPL-3.0.
 */

package com.movtery.zalithlauncher.ui.screens.main

import java.util.Locale
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.game.download.addon.platform.Platform

/**
 * Боковая панель навигации MCF Launcher (вместо верхней панели кнопок).
 */
@Composable
fun SideBar(
    inLauncherScreen: Boolean,
    inVersionsScreen: Boolean,
    inDownloadScreen: Boolean,
    inSettingsScreen: Boolean,
    activePlatform: Platform?,
    toMainScreen: () -> Unit,
    toVersionsScreen: () -> Unit,
    toDownloadScreen: () -> Unit,
    toPlatform: (Platform) -> Unit,
    toSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(200.dp)
            .fillMaxHeight()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SideBarItem(
            icon = R.drawable.ic_home_filled,
            text = stringResource(R.string.generic_main_menu),
            selected = inLauncherScreen,
            onClick = { if (!inLauncherScreen) toMainScreen() }
        )
        SideBarItem(
            icon = R.drawable.ic_build_filled,
            text = if (Locale.getDefault().language == "ru") "Установки" else stringResource(R.string.page_title_version_list),
            selected = inVersionsScreen,
            onClick = { if (!inVersionsScreen) toVersionsScreen() }
        )
        SideBarItem(
            icon = R.drawable.ic_download_2_filled,
            text = stringResource(R.string.generic_download),
            selected = inDownloadScreen && activePlatform == null,
            onClick = { if (!(inDownloadScreen && activePlatform == null)) toDownloadScreen() }
        )
        SideBarItem(
            icon = R.drawable.img_platform_curseforge,
            text = "CurseForge",
            selected = activePlatform == Platform.CURSEFORGE,
            colorIcon = true,
            onClick = { toPlatform(Platform.CURSEFORGE) }
        )
        SideBarItem(
            icon = R.drawable.img_platform_modrinth,
            text = "Modrinth",
            selected = activePlatform == Platform.MODRINTH,
            colorIcon = true,
            onClick = { toPlatform(Platform.MODRINTH) }
        )

        Spacer(modifier = Modifier.weight(1f))

        SideBarItem(
            icon = R.drawable.ic_settings_filled,
            text = stringResource(R.string.generic_setting),
            selected = inSettingsScreen,
            onClick = { if (!inSettingsScreen) toSettingsScreen() }
        )
        UnofficialBadge()
    }
}

@Composable
private fun SideBarItem(
    @DrawableRes icon: Int,
    text: String,
    selected: Boolean,
    colorIcon: Boolean = false,
    onClick: () -> Unit
) {
    val container = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
    val content = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(container)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (colorIcon) Color.Unspecified else content
        )
        Text(
            text = text,
            color = content,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}
