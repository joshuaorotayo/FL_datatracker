package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.data.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.data.repository.PresetRepository
import com.jorotayo.fl_datatracker.domain.usecase.DeleteFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.DeletePresetUseCase
import com.jorotayo.fl_datatracker.domain.usecase.GetPresetsUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SaveFieldUseCase
import com.jorotayo.fl_datatracker.domain.usecase.SavePresetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataFormModule {

    @Provides
    fun provideGetPresetsUseCase(repo: PresetRepository) =
        GetPresetsUseCase(repo)

    @Provides
    fun provideSavePresetUseCase(repo: PresetRepository) =
        SavePresetUseCase(repo)

    @Provides
    fun provideDeletePresetUseCase(
        presetRepo: PresetRepository,
        fieldRepo: DataFieldRepository
    ) = DeletePresetUseCase(presetRepo, fieldRepo)

    @Provides
    fun provideSaveFieldUseCase(repo: DataFieldRepository) =
        SaveFieldUseCase(repo)

    @Provides
    fun provideDeleteFieldUseCase(repo: DataFieldRepository) =
        DeleteFieldUseCase(repo)
}
