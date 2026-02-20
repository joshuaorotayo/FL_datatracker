package com.jorotayo.fl_datatracker.dependencyInjection

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jorotayo.fl_datatracker.data.model.MyObjectBox
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore =
        MyObjectBox.builder()
            .androidContext(context)
            .build()
}
