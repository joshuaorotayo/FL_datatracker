package com.jorotayo.fl_datatracker.screens.dataEntryScreen.components.formElements

import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall

@DefaultPreviews
@Composable
fun PreviewFormComponentTemplateV2() {
    FL_DatatrackerTheme {
        formComponentTemplateV2(fieldName = "Form Component Template Example")
    }
}

@Composable
fun formComponentTemplateV2(
    fieldName: String
): String {
    val count = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(xSmall)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = xxSmall, horizontal = small)
                    .fillMaxWidth(),
                text = fieldName,
                textAlign = Start,
                color = Gray,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = SpaceEvenly,
            verticalAlignment = CenterVertically
        ) {
            // Button Data capture
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(xSmall)
    )

    return count.value.toString()
}
