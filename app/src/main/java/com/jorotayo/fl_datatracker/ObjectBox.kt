package com.jorotayo.fl_datatracker

import android.content.Context
import com.jorotayo.fl_datatracker.domain.model.MyObjectBox
import io.objectbox.BoxStore


object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun get(): BoxStore {
        return boxStore;
    }
}