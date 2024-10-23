package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import androidx.lifecycle.ViewModel
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.jorotayo.fl_datatracker.domain.model.MemberStatus
import com.jorotayo.fl_datatracker.domain.model.Sonta
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.util.SharedSettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DataFieldsReworkViewModel @Inject constructor(
    private val dataFieldRepo: DataFieldRepository,
    private val sharedSettingService: SharedSettingService
) : ViewModel(), DataFieldsReworkInterface {
    private val _state = MutableStateFlow(DataFieldsReworkState())
    val state = _state.asStateFlow()

    override fun initView() {
//        sharedSettingService.showDashboardNavBar(false)
    }

    override fun onAddMembersClicked() {
        _state.update {
            it.copy(
                isAddMembersFormShowing = !state.value.isAddMembersFormShowing
            )
        }
    }

    override fun expandDropdown() {
        _state.update {
            it.copy(
                isDropdownExpanded = !state.value.isDropdownExpanded
            )
        }
    }

    val membersFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "firstName",
                initial = "",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "lastName",
                initial = "",
                validators = listOf(
                    Validators.Required()
                )
            ),
            SelectState(
                name = "gender",
                initial = mutableListOf("Male, Female"),
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "dob",
                initial = "",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "age",
                initial = "",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "cx",
                initial = "",
            ),
            TextFieldState(
                name = "addressLine2",
                initial = "",
            ),
            TextFieldState(
                name = "town",
                initial = "",
            ),
            TextFieldState(
                name = "postcode",
                initial = "",
            ),
            TextFieldState(
                name = "memberStatus",
                initial = "",
                transform = {
                    enumValueOf<MemberStatus>(it)
                }
            ),
            TextFieldState(
                name = "sonta",
                initial = "",
                transform = {
                    enumValueOf<Sonta>(it)
                },
                validators = listOf(
                    Validators.Required()
                )
            ),
            ChoiceState(
                name = "bacentaLeader",
                initial = "",
                transform = {
                    it == "True"
                },
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "constituencyOverseer",
                initial = "",
                transform = {
                    it == "True"
                },
                validators = listOf(
                    Validators.Required()
                )
            ),
        )
    )
}