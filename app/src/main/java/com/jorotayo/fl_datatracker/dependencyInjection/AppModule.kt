package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.data.repository.*
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.domain.useCases.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesSettings.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataFieldRepository(): DataFieldRepository {
        return DataFieldRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideDataFieldUseCases(dataFieldRepository: DataFieldRepository): DataFieldUseCases {
        return DataFieldUseCases(
            addDataField = AddDataField(dataFieldRepository),
            deleteDataField = DeleteDataField(dataFieldRepository),
            updateDataField = UpdateDataField(dataFieldRepository),
            getDataFields = GetDataFields(dataFieldRepository),
            getDataFieldsByPresetId = GetDataFieldsByPresetId(dataFieldRepository),
            getDataFieldsByPresetIdEnabled = GetDataFieldsByPresetIdEnabled(dataFieldRepository),
            getDataFieldByNames = GetDataFieldNames(dataFieldRepository),
            getDataFieldById = GetDataFieldById(dataFieldRepository),
            getDataFieldsByEnabled = GetDataFieldsByEnabled(dataFieldRepository),
            getSubscribedDataFields = GetSubscribedDataFields(dataFieldRepository),
            getDataFieldsFlow = GetDataFieldsFlow(dataFieldRepository)
        )
    }

    @Provides
    @Singleton
    fun providePresetRepository(): PresetRepositoryImpl {
        return PresetRepositoryImpl()
    }


    @Provides
    @Singleton
    fun providePresetUseCases(presetRepository: PresetRepositoryImpl): PresetUseCases {
        return PresetUseCases(
            getPresetByPresetName = GetPresetByPresetName(presetRepository),
            getPresetById = GetPresetById(presetRepository),
            getPresetList = GetPresetList(presetRepository),
            addPreset = AddPreset(presetRepository),
            deletePreset = DeletePreset(presetRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepositoryImpl {
        return DataRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideDataUseCases(dataRepository: DataRepositoryImpl): DataUseCases {
        return DataUseCases(
            addData = AddData(dataRepository),
            deleteData = DeleteData(dataRepository),
            deleteDataById = DeleteDataById(dataRepository),
            getDataByDataId = GetDataByDataId(dataRepository),
            getDataByDataName = GetDataByDataName(dataRepository),
            updateData = UpdateData(dataRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(): SettingsRepositoryImpl {
        return SettingsRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideSettingUseCases(settingsRepository: SettingsRepositoryImpl): SettingsUseCases {
        return SettingsUseCases(
            addSetting = AddSetting(settingsRepository),
            deleteSetting = DeleteSetting(settingsRepository),
            getSettingById = GetSettingById(settingsRepository),
            getSettingByValue = GetSettingByValue(settingsRepository),
            getSettingsByBool = GetSettingByBool(settingsRepository),
            getSettingsList = GetSettingsList(settingsRepository),
            getSettingByName = GetSettingByName(settingsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDataItemRepository(): DataItemRepositoryImpl {
        return DataItemRepositoryImpl()
    }


    @Provides
    @Singleton
    fun provideDataItemUseCases(dataItemRepository: DataItemRepositoryImpl): DataItemUseCases {
        return DataItemUseCases(
            addDataItem = AddDataItem(dataItemRepository),
            removeDataItem = RemoveDataItem(dataItemRepository),
            updateDataItem = UpdateDataItem(dataItemRepository),
            getDataItemList = GetDataItemList(dataItemRepository),
            getDataItemById = GetDataItemById(dataItemRepository),
            getDataItemsByPresetId = GetDataItemsByPresetId(dataItemRepository),
            getDataItemsEnabledByPresetId = GetDataItemsEnabledByPresetId(dataItemRepository)
        )
    }
}