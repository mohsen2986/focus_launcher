package com.mohsen.clarityhub.screens.launcher

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.internal.BackHandler
import dagger.hilt.components.SingletonComponent
import dev.mslalith.focuslauncher.core.screens.LauncherScreen
import com.mohsen.clarityhub.core.ui.providers.LocalLauncherPagerState
import com.mohsen.clarityhub.core.ui.providers.ProvideLauncherPagerState
import dev.mslalith.focuslauncher.feature.appdrawerpage.AppDrawerPage
import dev.mslalith.focuslauncher.feature.homepage.HomePage
import kotlinx.coroutines.launch

@CircuitInject(LauncherScreen::class, SingletonComponent::class)
@Composable
fun Launcher(
    state: LauncherState,
    modifier: Modifier = Modifier
) {
    ProvideLauncherPagerState {
        LauncherInternal(
            state = state,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LauncherInternal(
    state: LauncherState,
    modifier: Modifier = Modifier
) {
    val pagerState = LocalLauncherPagerState.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        when {
            pagerState.currentPage != 0 -> coroutineScope.launch {
                pagerState.animateScrollToPage(page = 0)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 2,
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .consumeWindowInsets(paddingValues = paddingValues)
        ) { page ->
            when (page) {
//                0 -> SettingsPage(state = state.settingsPageState)
                0 -> HomePage(state = state.homePageState)
                1 -> AppDrawerPage(state = state.appDrawerPageState)
            }
        }
    }
}
