package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PreviewDataFieldRowV2(){
    FL_DatatrackerTheme {
    DataFieldRowV2()
    }
}

@Composable
fun DataFieldRowV2() {

}