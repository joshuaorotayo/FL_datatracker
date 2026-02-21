package com.jorotayo.fl_datatracker

import android.app.Application
import com.jorotayo.fl_datatracker.data.model.MyObjectBox
import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxDataFieldRepository
import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxPresetRepository
import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxRecordRepository
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import dagger.hilt.android.HiltAndroidApp
import io.objectbox.BoxStore

@HiltAndroidApp
class DataTrackerApp : Application() {
    lateinit var store: BoxStore
        private set

    // Repositories are created once and reused
    val presetRepository: PresetRepository by lazy {
        ObjectBoxPresetRepository(store)
    }
    val dataFieldRepository: DataFieldRepository by lazy {
        ObjectBoxDataFieldRepository(store)
    }
    val recordRepository: RecordRepository by lazy {
        ObjectBoxRecordRepository(store)
    }

    override fun onCreate() {
        super.onCreate()
        store = MyObjectBox.builder()
            .androidContext(this)
            .build()
    }
}
