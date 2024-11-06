package com.jorotayo.fl_datatracker.screens.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TestState
import com.jorotayo.fl_datatracker.util.components.AlertDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    sealed class UiEvent {
        object DeleteDataItem : UiEvent()
    }

    private var _uiState = mutableStateOf(HomeScreenState())
    val uiState: MutableState<HomeScreenState> = _uiState

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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

    private fun onUpdateData(event: HomeScreenEvent.UpdateData) {
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
