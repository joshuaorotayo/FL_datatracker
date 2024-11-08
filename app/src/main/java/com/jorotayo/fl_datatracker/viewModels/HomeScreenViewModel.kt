package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import com.jorotayo.fl_datatracker.domain.useCases.DataItemUseCases
import com.jorotayo.fl_datatracker.domain.useCases.DataUseCases
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dataUseCases: DataUseCases,
    private val dataItemUseCases: DataItemUseCases,
) : ViewModel() {

    private var _uiState = mutableStateOf(HomeScreenState())
    val uiState: State<HomeScreenState> = _uiState

    private val _testRowItemBox = mutableStateOf(TestState())
    private val testRowItemBox: State<TestState> = _testRowItemBox

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ResetSearchBar -> onResetSearchBar()
            is HomeScreenEvent.SearchItemEntered -> onSearchItemEntered(event)
            is HomeScreenEvent.ToggleSearchBar -> onToggleSearchBar()
            is HomeScreenEvent.SearchFocusChanged -> onSearchFocusChanged(event)
            is HomeScreenEvent.EditDataItem -> onEditDataItem()
            is HomeScreenEvent.ToggleDeleteDataDialog -> onToggleDeleteDataDialog(event)
            is HomeScreenEvent.DeleteDataItem -> onDeleteDataItem()
            is HomeScreenEvent.UpdateData -> onUpdateData(event)
            is HomeScreenEvent.NavigateToDataEntry -> onNavigateToDataEntry()
        }
    }

    private fun onResetSearchBar() {
        _uiState.value = uiState.value.copy(
            text = ""
        )
    }

    private fun onSearchItemEntered(event: HomeScreenEvent.SearchItemEntered) {
        _uiState.value = uiState.value.copy(
            text = event.searchItem
        )
    }

    private fun onToggleSearchBar() {
        _uiState.value = uiState.value.copy(
            isSearchVisible = !uiState.value.isSearchVisible
        )
    }

    private fun onSearchFocusChanged(event: HomeScreenEvent.SearchFocusChanged) {
        _uiState.value = uiState.value.copy(
            isHintVisible = !event.focusState.isFocused
        )
    }

    private fun onEditDataItem() {

    }

    private fun onToggleDeleteDataDialog(event: HomeScreenEvent.ToggleDeleteDataDialog) {
        _uiState.value = uiState.value.copy(
            deletedItem = event.data,
            isDeleteDialogVisible = if (!uiState.value.isDeleteDialogVisible.value) mutableStateOf(
                true
            ) else mutableStateOf(false)
        )
    }

    private fun onDeleteDataItem() {
        /*val newBox = ObjectBox.get().boxFor(Data::class.java)
                newBox.remove(uiState.value.deletedItem)*/

        //delete the data item objects

        //delete the data object

        //refresh the data list
        val data = uiState.value.deletedItem
        val removeDataItems = dataItemUseCases.getDataItemListByDataAndPresetId(
            data.dataId,
            data.dataPresetId + 1
        )
        for (item in removeDataItems) {
            dataItemUseCases.removeDataItem(item)
        }

        dataUseCases.deleteData(data)

        _uiState.value = uiState.value.copy(
            dataList = dataUseCases.getData()
        )
    }

    private fun onUpdateData(event: HomeScreenEvent.UpdateData) {
        val newBox = ObjectBox.get().boxFor(TestRowItem::class.java)
        if (event.operation == "put") {
            newBox.put(event.testRowItem)
        } else {
            newBox.remove(event.testRowItem)
        }
        _testRowItemBox.value = testRowItemBox.value.copy(
            itemsList = newBox.all
        )
    }

    private fun onNavigateToDataEntry() {
        TODO("Not yet implemented")
    }
}