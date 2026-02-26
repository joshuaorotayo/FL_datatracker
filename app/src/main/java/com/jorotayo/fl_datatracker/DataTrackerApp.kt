package com.jorotayo.fl_datatracker

import android.app.Application
import com.getkeepsafe.relinker.ReLinker
import com.jorotayo.fl_datatracker.data.model.MyObjectBox
import com.jorotayo.fl_datatracker.data.model.Preset
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
            .androidReLinker { context: android.content.Context, library: String ->
                ReLinker.loadLibrary(context, library)
            }
            .build()

        seedDefaultPreset()
    }

    /**
     * Creates the "Default" preset on first install.
     * Checks if any presets exist before inserting so it only runs once —
     * reinstalling the app clears ObjectBox so it will reseed correctly.
     */
    private fun seedDefaultPreset() {
        val presets = presetRepository.getAllPresets()
        if (presets.isEmpty()) {
            presetRepository.savePreset(Preset(presetName = "Default"))
        }
    }
}
