package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.Setting
import com.jorotayo.fl_datatracker.domain.model.Setting_
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : ViewModel() {

    private val settingBox: Box<Setting> = ObjectBox.get().boxFor(Setting::class.java)
    private val query: Query<Setting> =
        settingBox.query(Setting_.settingName.equal("isOnBoardingComplete")).build()
    private val isOnBoardingComplete = query.findFirst()

    fun saveOnBoardingState() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnBoardingComplete!!.settingBoolValue == true) {
                isOnBoardingComplete.settingBoolValue = false
                Log.i(TAG,
                    "true launch: saveOnBoardingState: " + isOnBoardingComplete.settingBoolValue)
            } else {
                isOnBoardingComplete.settingBoolValue = true
            }
            settingBox.put(isOnBoardingComplete)
        }
    }
}
