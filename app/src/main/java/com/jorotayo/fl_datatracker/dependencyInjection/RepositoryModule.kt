package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxDataFieldRepository
import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxPresetRepository
import com.jorotayo.fl_datatracker.data.objectbox.ObjectBoxRecordRepository
import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import com.jorotayo.fl_datatracker.domain.usecase.GetFieldsForPresetUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPresetRepository(
        impl: ObjectBoxPresetRepository
    ): PresetRepository

    @Binds
    @Singleton
    abstract fun bindDataFieldRepository(
        impl: ObjectBoxDataFieldRepository
    ): DataFieldRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(
        impl: ObjectBoxRecordRepository
    ): RecordRepository

    companion object {
        @Provides
        fun provideGetFieldsUseCase(repo: DataFieldRepository) =
            GetFieldsForPresetUseCase(repo)
    }
}
