package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components.rowComponents

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewRowEdit(){
    FL_DatatrackerTheme {
        RowEdit()
    }
}

@Composable
fun RowEdit() {
    
}