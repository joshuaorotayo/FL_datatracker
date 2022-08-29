package com.jorotayo.fl_datatracker.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import com.jorotayo.fl_datatracker.navigation.Screen
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    private val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)
    private val query: Query<Setting> =
        settingBox.query(Setting_.settingName.equal("isOnBoardingComplete")).build()
    private val isOnBoardingComplete = query.findFirst()

    init {
        viewModelScope.launch {
            if (isOnBoardingComplete?.settingBoolValue == true) {
                _startDestination.value = Screen.HomeScreen.route
            } else {
                _startDestination.value = Screen.Welcome.route
            }
        }
        _isLoading.value = false
    }
}
