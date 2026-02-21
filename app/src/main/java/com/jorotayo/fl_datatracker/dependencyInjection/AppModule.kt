package com.jorotayo.fl_datatracker.dependencyInjection

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jorotayo.fl_datatracker.DataTrackerApp
import com.jorotayo.fl_datatracker.data.model.DataRecord
import com.jorotayo.fl_datatracker.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNavigationManager() = NavigationManager()

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore =
        (context.applicationContext as DataTrackerApp).store

    @Provides
    @Singleton
    fun provideDataRecordBox(store: BoxStore): Box<DataRecord> =
        store.boxFor(DataRecord::class.java)
}
