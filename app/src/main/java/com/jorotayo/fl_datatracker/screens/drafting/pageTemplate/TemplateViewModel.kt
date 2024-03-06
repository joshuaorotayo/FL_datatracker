package com.jorotayo.fl_datatracker.screens.drafting.pageTemplate

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.util.SharedSettingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val dataFieldRepo: DataFieldRepository,
    private val sharedSettingService: SharedSettingService
) : ViewModel(), TemplateInterface {
    private val _state = MutableStateFlow(TemplateState())
    val state = _state.asStateFlow()

    override fun initView() {
//        sharedSettingService.showDashboardNavBar(false)
    }

    override fun onAddMembersClicked() {
        _state.update {
            it.copy(
                showAddMembersForm = !state.value.showAddMembersForm
            )
        }
    }
}