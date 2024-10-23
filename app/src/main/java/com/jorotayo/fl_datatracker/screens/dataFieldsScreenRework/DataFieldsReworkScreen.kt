package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.jorotayo.fl_datatracker.domain.model.Gender
import com.jorotayo.fl_datatracker.domain.model.MemberStatus
import com.jorotayo.fl_datatracker.domain.model.Sonta
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.MinimalDateField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.minimalBooleanField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.minimalCountField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.minimalListField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.minimalSelectionField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.components.minimalShortTextField
import com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework.forms.MemberForm
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.DefaultSnackbar
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens

@DefaultPreviews
@Composable
fun PreviewDataFieldsScreenRework() {
    AppTheme {
        DataFieldsReworkScreen(
            state = DataFieldsReworkState(
                isAddMembersFormShowing = false,
                isDropdownExpanded = false,
                memberData = MemberForm(
                    memberId = 0L,
                    firstName = "",
                    lastName = "",
                    gender = Gender.MALE,
                    dob = "",
                    age = "",
                    addressLine1 = "",
                    addressLine2 = "",
                    town = "",
                    postcode = "",
                    memberStatus = MemberStatus.DEER,
                    sonta = Sonta.NO_MINISTRY,
                    bacentaLeader = false,
                    constituencyOverseer = false
                )

            ),
            viewModel = dataFieldsReworkPreview,
            formState = FormState(
                fields = listOf(
                    TextFieldState(
                        name = "firstName",
                        initial = "",
                        validators = listOf(
                            Validators.Required()
                        )
                    )
                )
            ),
        )
    }
}

@Composable
fun DataFieldsReworkView() {
    val viewModel = hiltViewModel<DataFieldsReworkViewModel>()
    val state by viewModel.state.collectAsState()

    DataFieldsReworkScreen(viewModel = viewModel, viewModel.membersFormState, state = state)

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DataFieldsReworkScreen(
    viewModel: DataFieldsReworkInterface,
    formState: FormState<BaseState<out Any>>,
    state: DataFieldsReworkState
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = dimens.large)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeaderRow()
                    AddMembers(viewModel::onAddMembersClicked)
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {

                minimalShortTextField(
                    rowHeader = "Members First Name",
                    rowHint = "First name",
                    textFieldState = formState.getState("firstName")
                )
                minimalShortTextField(
                    rowHeader = "Members Last Name",
                    rowHint = "Last name",
                    textFieldState = formState.getState("lastName")
                )

                minimalSelectionField(
                    rowHeader = "Gender",
                    selectionState = formState.getState("gender"),
                    state = state
                )

                MinimalDateField(
                    rowHeader = "Age",
                    dateFieldState = formState.getState("dob")
                )

                minimalShortTextField(
                    rowHeader = "Address Line 1",
                    rowHint = "Address 1: ",
                    textFieldState = formState.getState("addressLine1")
                )

                minimalShortTextField(
                    rowHeader = "Address Line 2",
                    rowHint = "Address 2: ",
                    textFieldState = formState.getState("addressLine2")
                )

                minimalShortTextField(
                    rowHeader = "Town",
                    rowHint = "Town: ",
                    textFieldState = formState.getState("town")
                )

                minimalShortTextField(
                    rowHeader = "Post code",
                    rowHint = "Postcode: ",
                    textFieldState = formState.getState("postcode")
                )

                minimalListField(rowHeader = "Row for list items", isError = false)

                minimalCountField(rowHeader = "Count row", isError = false)

                minimalBooleanField(rowHeader = "Boolean header", isError = false)
            }

            /*   AnimatedVisibility(
                   visible = state.isAddMembersFormShowing,
                   enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                   exit = fadeOut(animationSpec = tween(durationMillis = 300))
               )
               {
                   Column {
                       for (i in 1..4) {
                           minimalShortTextField(
                               rowHeader = "Header for row number: $i",
                               rowHint = "hint for row $i",
                           )
                       }
                   }
               }*/

            DefaultSnackbar(
                modifier = Modifier
                    .align(Alignment.Center),
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    if (scaffoldState.snackbarHostState.currentSnackbarData?.actionLabel?.contains(
                            "Restore"
                        ) == true
                    ) {
                        //function
                    }
                }
            )
        }
    }
}

@Composable
private fun HeaderRow() {
    Text(
        modifier = Modifier.padding(start = dimens.small),
        text = "Data Fields",
        color = colors.primary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Start
    )
}


@Composable
fun AddMembers(onAddMembersClick: () -> Unit) {
    IconButton(
        modifier = Modifier,
        onClick = onAddMembersClick
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add Members Button",
            tint = colors.primary,
            modifier = Modifier.background(
                shape = CircleShape,
                color = colors.surface.copy(alpha = dimens.fiftyPercent)
            )
        )
    }
}

@Composable
fun AddMembersForm(viewModel: DataFieldsReworkInterface, state: DataFieldsReworkState) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimens.xSmall),
        elevation = dimens.xSmall,
        contentColor = Color.Blue,
        shape = RoundedCornerShape(dimens.small)
    ) {

    }
}
