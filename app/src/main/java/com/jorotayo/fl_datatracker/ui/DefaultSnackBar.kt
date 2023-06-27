package com.jorotayo.fl_datatracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                    .padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2,
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
