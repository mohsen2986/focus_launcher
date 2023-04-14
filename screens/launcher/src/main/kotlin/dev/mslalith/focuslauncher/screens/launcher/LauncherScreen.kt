package dev.mslalith.focuslauncher.screens.launcher

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mslalith.focuslauncher.core.ui.BackPressHandler
import dev.mslalith.focuslauncher.core.ui.providers.LocalLauncherViewManager
import dev.mslalith.focuslauncher.feature.appdrawerpage.AppDrawerPage
import dev.mslalith.focuslauncher.feature.homepage.HomePage
import dev.mslalith.focuslauncher.feature.settingspage.SettingsPage
import kotlinx.coroutines.launch

@Composable
fun LauncherScreen() {
    LauncherScreen(
        launcherViewModel = hiltViewModel()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LauncherScreen(
    launcherViewModel: LauncherViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1)

    val viewManager = LocalLauncherViewManager.current

    LaunchedEffect(key1 = Unit) {
        launcherViewModel.loadApps()
    }

    PackageActionListener(onAction = launcherViewModel::onPackageAction)

    BackPressHandler(enabled = true) {
        viewManager.apply {
            when {
                isDialogVisible -> hideDialog()
                isVisible -> hideBottomSheet()
                pagerState.currentPage != 1 -> coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = viewManager.scaffoldState,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HorizontalPager(
            pageCount = 3,
            state = pagerState,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
        ) { page ->
            when (page) {
                0 -> SettingsPage()
                1 -> HomePage(
                    pagerCurrentPage = snapshotFlow { pagerState.currentPage }
                )

                2 -> AppDrawerPage()
            }
        }
    }
}