package com.jorotayo.fl_datatracker.dependencyInjection

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jorotayo.fl_datatracker.data.repository.*
import com.jorotayo.fl_datatracker.domain.repository.DataFieldRepository
import com.jorotayo.fl_datatracker.domain.useCases.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesData.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataField.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesDataItem.*
import com.jorotayo.fl_datatracker.domain.useCases.useCasesPreset.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Provides Singleton]
    fun provideDataFieldRepository(): DataFieldRepository {
        return DataFieldRepositoryImpl()
    }

    @[Provides Singleton]
    fun provideDataFieldUseCases(dataFieldRepository: DataFieldRepository): DataFieldUseCases {
        return DataFieldUseCases(
            addDataField = AddDataField(dataFieldRepository),
            deleteDataField = DeleteDataField(dataFieldRepository),
            deleteDataFields = DeleteDataFields(dataFieldRepository),
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

    @[Provides Singleton]
    fun providePresetRepository(): PresetRepositoryImpl {
        return PresetRepositoryImpl()
    }

    @[Provides Singleton]
    fun providePresetUseCases(presetRepository: PresetRepositoryImpl): PresetUseCases {
        return PresetUseCases(
            getPresetByPresetName = GetPresetByPresetName(presetRepository),
            getPresetById = GetPresetById(presetRepository),
            getPresetList = GetPresetList(presetRepository),
            addPreset = AddPreset(presetRepository),
            deletePreset = DeletePreset(presetRepository),
            getCurrentPresetFromSettings = GetCurrentPresetFromSettings(presetRepository)
        )
    }

    @[Provides Singleton]
    fun provideDataRepository(): DataRepositoryImpl {
        return DataRepositoryImpl()
    }

    @[Provides Singleton]
    fun provideDataUseCases(dataRepository: DataRepositoryImpl): DataUseCases {
        return DataUseCases(
            getData = GetData(dataRepository),
            addData = AddData(dataRepository),
            deleteData = DeleteData(dataRepository),
            deleteDataById = DeleteDataById(dataRepository),
            getDataByDataId = GetDataByDataId(dataRepository),
            getDataByDataName = GetDataByDataName(dataRepository),
            updateData = UpdateData(dataRepository),
            validateInsertDataForm = ValidateInsertDataForm()
        )
    }

    @[Provides Singleton]
    fun provideDataItemRepository(): DataItemRepositoryImpl {
        return DataItemRepositoryImpl()
    }

    @[Provides Singleton]
    fun provideDataItemUseCases(dataItemRepository: DataItemRepositoryImpl): DataItemUseCases {
        return DataItemUseCases(
            addDataItem = AddDataItem(dataItemRepository),
            removeDataItem = RemoveDataItem(dataItemRepository),
            updateDataItem = UpdateDataItem(dataItemRepository),
            getDataItemList = GetDataItemList(dataItemRepository),
            getDataItemById = GetDataItemById(dataItemRepository),
            getDataItemsListByDataId = GetDataItemsListByDataId(dataItemRepository),
            getDataItemsByPresetId = GetDataItemsByPresetId(dataItemRepository),
            getDataItemListByDataAndPresetId = GetDataItemListByDataAndPresetId(dataItemRepository),
            getDataItemsEnabledByPresetId = GetDataItemsEnabledByPresetId(dataItemRepository)
        )
    }

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppPreferences", MODE_PRIVATE)
    }
}