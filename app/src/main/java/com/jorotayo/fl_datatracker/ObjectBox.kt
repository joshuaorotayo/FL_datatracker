package com.jorotayo.fl_datatracker

import android.content.Context
import android.util.Log
import com.jorotayo.fl_datatracker.domain.model.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.Admin


object ObjectBox {
    private lateinit var boxStore: BoxStore
    lateinit var admin: Admin
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()


        if (BuildConfig.DEBUG) {
            val started = Admin(boxStore).start(context.applicationContext)
            Log.i("ObjectBoxAdmin", "Started: $started")
        }
    }


    fun boxStore(): BoxStore {
        return boxStore
    }
}