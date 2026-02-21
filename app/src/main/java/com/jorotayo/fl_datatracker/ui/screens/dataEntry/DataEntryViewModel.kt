package com.jorotayo.fl_datatracker.ui.screens.dataEntry

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.ValidateFieldEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DataEntryViewModel @Inject constructor(
    private val getFields: GetFieldsForPresetUseCase,
    private val saveRecord: SaveRecordUseCase,
    private val validateEntry: ValidateFieldEntryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DataEntryState())
    val state = _state.asStateFlow()

    fun onEvent(event: DataEntryEvent) {
        when (event) {
            is DataEntryEvent.LoadPreset -> TODO()
            is DataEntryEvent.UpdateValue -> TODO()
            DataEntryEvent.Clear -> TODO()
            DataEntryEvent.Submit -> TODO()
        }
    }
}