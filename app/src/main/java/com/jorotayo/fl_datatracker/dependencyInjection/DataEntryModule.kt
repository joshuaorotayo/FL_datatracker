package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.data.repository.RecordRepository
import com.jorotayo.fl_datatracker.domain.usecase.SaveRecordUseCase
import com.jorotayo.fl_datatracker.domain.usecase.ValidateFieldEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataEntryModule {

    @Provides
    fun provideValidateFieldEntryUseCase() =
        ValidateFieldEntryUseCase()

    @Provides
    fun provideSaveRecordUseCase(
        repo: RecordRepository,
        validate: ValidateFieldEntryUseCase
    ) = SaveRecordUseCase(repo, validate)

}