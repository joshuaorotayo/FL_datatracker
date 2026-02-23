package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen

import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.domain.usecase.DeleteFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.DeletePresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SavePresetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DataFormViewModel @Inject constructor(
    private val getPresets: GetPresetsUseCase,
    private val savePreset: SavePresetUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val getFields: GetFieldsForPresetUseCase,
    private val saveField: SaveFieldUseCase,
    private val deleteField: DeleteFieldUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DataFieldsState())
    val state = _state.asStateFlow()

    fun onEvent(event: DataFormEvent) {
        when (event) {
            is DataFormEvent.DeleteField -> TODO()
            is DataFormEvent.DeletePreset -> TODO()
            is DataFormEvent.SaveField -> TODO()
            is DataFormEvent.SavePreset -> TODO()
            is DataFormEvent.SelectPreset -> TODO()
            is DataFormEvent.AddField -> TODO()
        }
    }
}
