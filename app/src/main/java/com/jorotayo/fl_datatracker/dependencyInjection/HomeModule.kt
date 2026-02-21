package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import com.jorotayo.fl_datatracker.domain.usecase.DeleteRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetAllRecordsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetRecordEntriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun provideGetRecordsUseCase(repo: RecordRepository) =
        GetAllRecordsUseCase(repo)

    @Provides
    fun provideDeleteRecordUseCase(repo: RecordRepository) =
        DeleteRecordUseCase(repo)

    @Provides
    fun provideGetRecordEntriesUseCase(repo: RecordRepository) =
        GetRecordEntriesUseCase(repo)
}