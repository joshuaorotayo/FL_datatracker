package com.jorotayo.fl_datatracker.screens.dataFieldsScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.domain.model.DataField

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewDeleteDialog() {
}

@Composable
fun DeleteDialog(
    modifier: Modifier,
    confirmDelete: () -> Unit,
    state: MutableState<Boolean>,
    dataField: DataField
) {
    /* if (state.value) {
         AlertDialog(
             onDismissRequest = {
                 // Dismiss the dialog when the user clicks outside the dialog or on the back
                 // button. If you want to disable that functionality, simply use an empty
                 // onCloseRequest.
                 state.value = false
             },
             title = {
                 Text(text = "Dialog Title")
             },
             text = {
                 Text("Here is a text ")
             },
             confirmButton = {
                 Button(

                     onClick = {
                         state.value = false
                     }) {
                     Text("This is the Confirm Button")
                 }
             },
             dismissButton = {
                 Button(

                     onClick = {
                         state.value = false
                     }) {
                     Text("This is the dismiss Button")
                 }
             }
         )
     }*/

    if (state.value) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(Color.White)
            ) {

                //.......................................................................
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete dialog, delete icon", // decorative
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),

                    )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Delete row?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Are you sure you want to delete this Data Field?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = dataField.fieldName,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                    )
                }
                //.......................................................................
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colors.surface),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {
                        state.value = false
                    }) {

                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        state.value = false
                        // TODO: Delete Dialog Button
                    }) {
                        Text(
                            "Delete",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
    /*  if (state.value) {

          Card(
              elevation = 4.dp,
              modifier = modifier
                  .wrapContentSize()
                  .padding(horizontal = 16.dp)

          ) {

              Column(modifier = modifier) {
                  Image(
                      imageVector = Icons.Default.Delete,
                      contentDescription = "Delete Icon for dialog",
                      contentScale = ContentScale.Fit,
                      colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                      modifier = Modifier
                          .padding(top = 20.dp)
                          .height(70.dp)
                          .fillMaxWidth()
                  )

                  Text(
                      text = "Delete Data Field",
                      textAlign = TextAlign.Center,
                      modifier = Modifier
                          .padding(top = 5.dp)
                          .fillMaxWidth(),
                      style = MaterialTheme.typography.h4,
                      maxLines = 2,
                      overflow = TextOverflow.Ellipsis
                  )
                  Text(
                      text = "This data field will be deleted and need to be re-added to be used",
                      textAlign = TextAlign.Center,
                      modifier = Modifier
                          .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                          .fillMaxWidth(),
                      style = MaterialTheme.typography.body2
                  )

                  Row(
                      Modifier
                          .fillMaxWidth()
                          .padding(top = 10.dp)
                          .background(MaterialTheme.colors.onBackground),
                      horizontalArrangement = Arrangement.SpaceAround
                  ) {

                      TextButton(onClick = {
                          state.value = false
                      }) {

                          Text(
                              "Cancel",
                              fontWeight = FontWeight.Bold,
                              color = Color.LightGray,
                              modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                          )
                      }
                      TextButton(
                          onClick =
                          confirmDelete
                      ) {
                          Text(
                              "Delete",
                              fontWeight = FontWeight.ExtraBold,
                              color = MaterialTheme.colors.primary,
                              modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                          )
                      }
                  }
              }
          }
      }*/

}