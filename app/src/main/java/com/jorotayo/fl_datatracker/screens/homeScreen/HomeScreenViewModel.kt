package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.DeleteDataItem
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.EditDataItem
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.NavigateToDataEntry
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.ResetSearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.SearchFocusChanged
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.SearchItemEntered
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.ToggleDeleteDataDialog
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.ToggleSearchBar
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent.UpdateData
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TestState
import com.jorotayo.fl_datatracker.util.components.AlertDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    sealed class UiEvent {
        object DeleteDataItem : UiEvent()
    }

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _testRowItemBox = mutableStateOf(TestState())
    private val testRowItemBox: State<TestState> = _testRowItemBox

    fun onHomeEvent(event: HomeScreenEvent) {
        when (event) {
            is ResetSearchBar -> onResetSearchBar()
            is SearchItemEntered -> onSearchItemEntered(event)
            is ToggleSearchBar -> onToggleSearchBar()
            is SearchFocusChanged -> onSearchFocusChanged(event)
            is EditDataItem -> onEditDataItem()
            is ToggleDeleteDataDialog -> onToggleDeleteDataDialog(event)
            is DeleteDataItem -> onDeleteDataItem()
            is UpdateData -> onUpdateData(event)
            is NavigateToDataEntry -> onNavigateToDataEntry()
        }
    }

    private fun onResetSearchBar() {
        _uiState.value = uiState.value.copy(
            text = ""
        )
    }

    private fun onSearchItemEntered(event: SearchItemEntered) {
        _uiState.value = uiState.value.copy(
            text = event.searchItem
        )
    }

    private fun onToggleSearchBar() {
        _uiState.value = uiState.value.copy(
            isSearchVisible = !uiState.value.isSearchVisible
        )
    }

    private fun onSearchFocusChanged(event: SearchFocusChanged) {
        _uiState.value = uiState.value.copy(
            isHintVisible = !event.focusState.isFocused
        )
    }

    private fun onEditDataItem() {
    }

    private fun onToggleDeleteDataDialog(event: ToggleDeleteDataDialog) {
        _uiState.value = uiState.value.copy(
            deletedItem = event.data,
            alertDialogState = AlertDialogState(
                title = String.format("Delete Data Item: %s", event.data.name),
                imageIcon = Icons.Default.Delete,
                body = "Are you sure you want to delete this Data item?",
                onDismissRequest = { dismissAlertDialog() },
                confirmButtonLabel = "Delete",
                confirmButtonOnClick = {
                    onDeleteDataItem()
                    dismissAlertDialog()
                },
                dismissButtonLabel = "Cancel",
                dismissButtonOnClick = { dismissAlertDialog() },
                titleTextAlign = TextAlign.Center,
                dismissible = true
            )
        )
    }

    private fun dismissAlertDialog() {
        _uiState.value = uiState.value.copy(
            alertDialogState = null
        )
    }

    private fun onDeleteDataItem() {
        /*val newBox = ObjectBox.get().boxFor(Data::class.java)
                newBox.remove(uiState.value.deletedItem)*/

        // delete the data item objects

        // delete the data object

        // refresh the data list
        val data = uiState.value.deletedItem
        val removeDataItems = repository.getDataItemListByDataAndPresetId(
            data.dataId,
            data.dataPresetId
        )
        for (item in removeDataItems) {
            repository.removeDataItem(item)
        }

        repository.deleteData(data)

        _uiState.value = uiState.value.copy(
            dataList = repository.getData()
        )
    }

    private fun onUpdateData(event: UpdateData) {
        val newBox = ObjectBox.boxStore().boxFor(TestRowItem::class.java)
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
