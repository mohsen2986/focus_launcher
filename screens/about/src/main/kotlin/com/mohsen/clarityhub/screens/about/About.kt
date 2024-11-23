package com.mohsen.clarityhub.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent
import dev.mslalith.focuslauncher.core.screens.AboutScreen
import com.mohsen.clarityhub.core.ui.AppBarWithBackIcon
import com.mohsen.clarityhub.core.ui.FillSpacer
import com.mohsen.clarityhub.core.ui.VerticalSpacer
import com.mohsen.clarityhub.screens.about.ui.AppInfo
import com.mohsen.clarityhub.screens.about.ui.Credits
import com.mohsen.clarityhub.screens.about.ui.MadeWithLove

@CircuitInject(AboutScreen::class, SingletonComponent::class)
@Composable
fun About(
    state: AboutState,
    modifier: Modifier = Modifier
) {
    // Need to extract the eventSink out to a local val, so that the Compose Compiler
    // treats it as stable. See: https://issuetracker.google.com/issues/256100927
    val eventSink = state.eventSink

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AppBarWithBackIcon(
                title = stringResource(id = R.string.about),
                onBackPressed = { eventSink(AboutUiEvent.GoBack) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FillSpacer()
            AppInfo()
            FillSpacer()

            Credits()
            VerticalSpacer(spacing = 24.dp)
            MadeWithLove()
        }
    }
}
