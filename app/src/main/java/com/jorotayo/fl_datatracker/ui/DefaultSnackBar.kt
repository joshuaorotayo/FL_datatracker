package com.jorotayo.fl_datatracker.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.small
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@DefaultDualPreview
@Composable
fun SnackBarPreview() {
    FL_DatatrackerTheme {
        val scope = rememberCoroutineScope()
        val host = SnackbarHostState()

        DefaultSnackbar(
            snackbarHostState = host,
            onDismiss = {}
        )
        scope.launch {
            host.showSnackbar(
                message = "Preview snackbar",
                actionLabel = "RELOAD",
                duration = androidx.compose.material.SnackbarDuration.Indefinite
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit?
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier
                    .padding(small),
                shape = RoundedCornerShape(small),
                content = {
                    Text(
                        text = data.message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
    )
}
