package com.jorotayo.fl_datatracker

import com.jorotayo.fl_datatracker.domain.model.Data
import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.screens.homeScreen.HomeScreenViewModel
import com.jorotayo.fl_datatracker.screens.homeScreen.components.HomeScreenEvent
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class HomeScreenViewModelTest {

    private lateinit var repository: AppRepository
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        repository = mock() // Mock AppRepository
        viewModel = HomeScreenViewModel(repository)
    }


    @Test
    fun `onResetSearchBar should clear text in uiState`() {
        // Trigger ResetSearchBar event
        viewModel.onHomeEvent(HomeScreenEvent.ResetSearchBar)

        // Assert that uiState.text is cleared
        assertEquals("", viewModel.uiState.value.text)
    }


    @Test
    fun `onSearchItemEntered should update text in uiState`() {
        // Trigger SearchItemEntered event
        val searchQuery = "TestQuery"
        viewModel.onHomeEvent(HomeScreenEvent.SearchItemEntered(searchQuery))

        // Assert that uiState.text is updated
        assertEquals(searchQuery, viewModel.uiState.value.text)
    }

    @Test
    fun `onToggleDeleteDataDialog should set AlertDialogState`(): Unit {
        // Prepare a mock data item
        val mockData = mock<Data>()
        whenever(repository.getDataByDataId(0)) doReturn Data(
            dataId = 0,
            dataPresetId = 0,
            name = "Darnell",
            lastEditedTime = "Murphy",
            createdTime = "Alan"
        )

        // Trigger ToggleDeleteDataDialog event
        viewModel.onHomeEvent(HomeScreenEvent.ToggleDeleteDataDialog(mockData))

        // Assert that alertDialogState is set
        val alertDialogState = viewModel.uiState.value.alertDialogState
        assertNotNull(alertDialogState)
        assertEquals("Delete Data Item: Mock Data", alertDialogState?.title)
    }

    @Test
    fun `onDeleteDataItem should delete data and update dataList`() {
        // Arrange: Mock repository behavior
        val mockData = mock<Data>()
        whenever(
            repository.getDataItemListByDataAndPresetId(
                any(),
                any()
            )
        ).thenReturn(listOf(mock()))

        // Simulate setting deletedItem in uiState
        viewModel.onHomeEvent(HomeScreenEvent.ToggleDeleteDataDialog(mockData))
        viewModel.onHomeEvent(HomeScreenEvent.DeleteDataItem)

        // Verify repository interactions
        verify(repository).deleteData(mockData)
        verify(repository).getData()
    }

//    @Test
//    fun `onNavigateToDataEntry should emit UiEvent`() = runTest {
//        // Trigger NavigateToDataEntry event
//        viewModel.onHomeEvent(HomeScreenEvent.NavigateToDataEntry)
//
//        // Collect UiEvent emitted in eventFlow
//        viewModel.eventFlow.collect {
//            assertEquals(viewModel.onHomeEvent(HomeScreenEvent.NavigateToDataEntry, awaitItem())
//        }
//    }
}
