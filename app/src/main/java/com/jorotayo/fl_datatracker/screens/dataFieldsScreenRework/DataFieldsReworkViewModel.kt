package com.jorotayo.fl_datatracker.screens.dataFieldsScreenRework

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.util.SharedSettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DataFieldsReworkViewModel @Inject constructor(
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
}