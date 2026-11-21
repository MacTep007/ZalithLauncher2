/*
 * MCF Launcher is an unofficial modified version of Zalith Launcher 2
 * (Copyright (C) 2025-2026 MovTery and contributors), licensed under GPL-3.0.
 */

package com.movtery.zalithlauncher.ui.screens.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movtery.zalithlauncher.R
import com.movtery.zalithlauncher.game.account.AccountsManager
import com.movtery.zalithlauncher.game.account.getAccountTypeName
import com.movtery.zalithlauncher.game.version.installed.Version
import com.movtery.zalithlauncher.game.version.installed.VersionsManager
import com.movtery.zalithlauncher.ui.base.BaseScreen
import com.movtery.zalithlauncher.ui.screens.NestedNavKey
import com.movtery.zalithlauncher.ui.screens.NormalNavKey
import com.movtery.zalithlauncher.ui.screens.content.elements.CommonVersionInfoLayout
import com.movtery.zalithlauncher.ui.screens.content.elements.PlayerFace
import com.movtery.zalithlauncher.ui.screens.content.elements.VersionIconImage
import com.movtery.zalithlauncher.viewmodel.ScreenBackStackViewModel
import java.util.Locale

private val PlayGreen = Color(0xFF2E7D32)

/**
 * Новый главный экран MCF Launcher: картинка, снизу выбор версии, кнопка «Играть» и аккаунт.
 */
@Composable
fun PlayHomeScreen(
    backStackViewModel: ScreenBackStackViewModel,
    onLaunchGame: (Version?) -> Unit,
) {
    BaseScreen(
        screenKey = NormalNavKey.LauncherMain,
        currentKey = backStackViewModel.mainScreen.currentKey
    ) { _ ->
        val toAccountManageScreen: () -> Unit = {
            backStackViewModel.mainScreen.navigateTo(
                screenKey = NormalNavKey.AccountManager()
            )
        }
        val toVersionManageScreen: () -> Unit = {
            backStackViewModel.mainScreen.removeAndNavigateTo(
                remove = NestedNavKey.VersionSettings::class,
                screenKey = NormalNavKey.VersionsManager
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.launcher_hero),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            //затемнение снизу, чтобы текст читался на любой картинке
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))
                    )
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VersionSelector(
                    modifier = Modifier.weight(1f),
                    toVersionManageScreen = toVersionManageScreen
                )

                PlayButton(onClick = { onLaunchGame(null) })

                AccountBlock(
                    modifier = Modifier.weight(1f),
                    onClick = toAccountManageScreen
                )
            }
        }
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    val text = if (Locale.getDefault().language == "ru") "ИГРАТЬ" else "PLAY"
    Button(
        modifier = Modifier
            .width(220.dp)
            .height(56.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PlayGreen,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun VersionSelector(
    toVersionManageScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val version0 by VersionsManager.currentVersion.collectAsStateWithLifecycle()
    val versions by VersionsManager.versions.collectAsStateWithLifecycle()
    val version = version0
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    if (version == null || versions.isEmpty()) toVersionManageScreen()
                    else expanded = true
                }
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (version != null) {
                VersionIconImage(
                    modifier = Modifier.size(40.dp),
                    version = version
                )
                Column {
                    Text(
                        text = version.getVersionName(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (version.isValid()) {
                        Text(
                            text = version.getVersionSummary(),
                            color = Color(0xFFD0D0D0),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_rounded),
                    contentDescription = null,
                    tint = Color.White
                )
            } else {
                Text(
                    text = stringResource(R.string.versions_manage_no_versions),
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        DropdownMenu(
            expanded = expanded && versions.isNotEmpty(),
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(260.dp)
        ) {
            versions.forEach { item ->
                DropdownMenuItem(
                    text = {
                        CommonVersionInfoLayout(
                            modifier = Modifier.fillMaxWidth(),
                            version = item,
                            iconSize = 28.dp
                        )
                    },
                    onClick = {
                        if (version != item) VersionsManager.saveVersion(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun AccountBlock(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val account0 by AccountsManager.currentAccountFlow.collectAsStateWithLifecycle()
    val account = account0

    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = account?.username ?: stringResource(R.string.account_add_new_account),
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (account != null) {
                    Text(
                        text = getAccountTypeName(account),
                        color = Color(0xFFD0D0D0),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                }
            }
            if (account != null) {
                PlayerFace(
                    account = account,
                    avatarSize = 40.dp
                )
            } else {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
