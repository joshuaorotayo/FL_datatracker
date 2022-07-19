package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PreviewFormComponentTemplate() {
    FormComponentTemplate()
}

@Composable
fun FormComponentTemplate(
    //insert any import variables
) {
    //define any local variables

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                text = "TEST",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .shadow(elevation = (4.dp))
                    .background(MaterialTheme.colors.onPrimary),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text(
                    text = "Template Text",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f, fill = false)
                        .clickable(
                            onClick = {
                                /// TODO: Add Onclick function for method
                            }
                        ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )

            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}