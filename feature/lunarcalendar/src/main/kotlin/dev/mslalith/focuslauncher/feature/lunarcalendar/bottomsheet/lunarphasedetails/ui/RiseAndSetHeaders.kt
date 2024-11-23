package dev.mslalith.focuslauncher.feature.lunarcalendar.bottomsheet.lunarphasedetails.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mohsen.clarityhub.core.ui.FillSpacer
import dev.mslalith.focuslauncher.feature.lunarcalendar.R

@Composable
internal fun RiseAndSetHeaders(
    contentColor: Color
) {
    Row {
        Text(
            text = stringResource(id = R.string.moon),
            color = contentColor,
            style = MaterialTheme.typography.titleMedium
        )
        FillSpacer()
        Text(
            text = stringResource(id = R.string.sun),
            color = contentColor,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
