package com.jorotayo.fl_datatracker.dependencyInjection

import com.jorotayo.fl_datatracker.domain.repository.AppRepository
import com.jorotayo.fl_datatracker.domain.repository.DataItemRepository
import com.jorotayo.fl_datatracker.domain.repository.DataRepository
import com.jorotayo.fl_datatracker.domain.repository.FieldRepository
import com.jorotayo.fl_datatracker.domain.repository.PresetRepository
import com.jorotayo.fl_datatracker.domain.repositoryImpl.AppRepositoryImpl
import com.jorotayo.fl_datatracker.domain.repositoryImpl.DataItemRepositoryImpl
import com.jorotayo.fl_datatracker.domain.repositoryImpl.DataRepositoryImpl
import com.jorotayo.fl_datatracker.domain.repositoryImpl.FieldRepositoryImpl
import com.jorotayo.fl_datatracker.domain.repositoryImpl.PresetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataFieldRepository(): FieldRepository {
        return FieldRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDataItemRepository(): DataItemRepository {
        return DataItemRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepositoryImpl()
    }

    /*
        @Provides
        @Singleton fun provideMemberRepository(): MemberRepository {
            return MemberRepositoryImpl()
        }
    */

    @Provides
    @Singleton
    fun providePresetRepository(): PresetRepository {
        return PresetRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        fieldRepository: FieldRepository,
        dataItemRepository: DataItemRepository,
        dataRepository: DataRepository,
        presetRepository: PresetRepository
    ): AppRepository {
        return AppRepositoryImpl(
            fieldRepository,
            dataItemRepository,
            dataRepository,
            presetRepository
        )
    }
}