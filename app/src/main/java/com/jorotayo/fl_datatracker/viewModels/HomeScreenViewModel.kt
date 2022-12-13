package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.model.TestRowItem
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenState
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import com.jorotayo.fl_datatracker.screens.homeScreen.components.TestState
import com.jorotayo.fl_datatracker.util.BoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private var _uiState = mutableStateOf(
        HomeScreenState(
            isSearchVisible = false,
            text = "",
            hint = "Search for data",
            isHintVisible = true,
            dataList = ObjectBox.get().boxFor(Data::class.java).all,
            deletedItem = Data(
                dataId = 0,
                name = "test",
                lastEditedTime = "yesterday",
                createdTime = "today"
            ),
            isDeleteDialogVisible = mutableStateOf(false)
        )
    )
    val uiState: State<HomeScreenState> = _uiState

    private val _testRowItemBox = mutableStateOf(TestState())

    val testRowItemBox: State<TestState> = _testRowItemBox

    private val _boxState = mutableStateOf(BoxState())
    val boxState: State<BoxState> = _boxState

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ResetSearchBar -> {
                _uiState.value = uiState.value.copy(
                    text = ""
                )
            }
            is HomeScreenEvent.SearchItemEntered -> {
                _uiState.value = uiState.value.copy(
                    text = event.searchItem
                )
            }
            is HomeScreenEvent.ToggleSearchBar -> {
                _uiState.value = uiState.value.copy(
                    isSearchVisible = !uiState.value.isSearchVisible
                )
            }
            is HomeScreenEvent.SearchFocusChanged -> {
                _uiState.value = uiState.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
            HomeScreenEvent.EditDataItem -> TODO()
            HomeScreenEvent.ShowSettingsView -> {
                //show Settings TODO()
            }

            is HomeScreenEvent.ToggleDeleteDataDialog -> {

                _uiState.value = uiState.value.copy(
                    deletedItem = event.dataItem,
                    isDeleteDialogVisible = if (!uiState.value.isDeleteDialogVisible.value) mutableStateOf(
                        true) else mutableStateOf(false)
                )
            }
            is HomeScreenEvent.DeleteDataItem -> {
                val newBox = ObjectBox.get().boxFor(Data::class.java)
                newBox.remove(uiState.value.deletedItem)

                _uiState.value = uiState.value.copy(
                    dataList = newBox.all
                )
            }

            /*  is HomeScreenEvent.UpdateData -> {
                  _testRowItemBox.value = testRowItemBox.value.copy(
                      itemsList = event.value.all
                  )
              }*/

            is HomeScreenEvent.UpdateData -> {
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
        }
    }
}