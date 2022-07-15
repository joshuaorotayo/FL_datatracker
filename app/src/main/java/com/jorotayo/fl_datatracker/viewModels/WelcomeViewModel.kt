package com.jorotayo.fl_datatracker.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorotayo.fl_datatracker.ObjectBox
import com.jorotayo.fl_datatracker.domain.model.SettingsBool
import com.jorotayo.fl_datatracker.domain.model.SettingsBool_
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : ViewModel() {

    private val settingBox: Box<SettingsBool> = ObjectBox.get().boxFor(SettingsBool::class.java)
    private val query: Query<SettingsBool> =
        settingBox.query(SettingsBool_.settingName.equal("isOnBoardingComplete")).build()
    private val isOnBoardingComplete = query.findFirst()


    /***
     * @param checkedState Value returned from the checkbox in the landing page section
     */
    fun saveOnBoardingState() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isOnBoardingComplete!!.settingValue) {
                isOnBoardingComplete.settingValue = false
                Log.i(TAG, "true launch: saveOnBoardingState: " + isOnBoardingComplete.settingValue)
            } else {
                isOnBoardingComplete.settingValue = true
                Log.i(
                    TAG,
                    "false launch: saveOnBoardingState: " + isOnBoardingComplete.settingValue
                )
            }
            settingBox.put(isOnBoardingComplete)
        }
    }

}
