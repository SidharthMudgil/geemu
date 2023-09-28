package com.sidharth.geemu.di

import com.sidharth.geemu.data.local.LocalDataSource
import com.sidharth.geemu.data.remote.source.RemoteDataSource
import com.sidharth.geemu.data.repository.GameRepositoryImpl
import com.sidharth.geemu.data.repository.UserDataRepositoryImpl
import com.sidharth.geemu.domain.repository.GameRepository
import com.sidharth.geemu.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGameRepository(
        remoteDataSource: RemoteDataSource
    ): GameRepository {
        return GameRepositoryImpl(
            remoteDataSource = remoteDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        localDataSource: LocalDataSource
    ): UserDataRepository {
        return UserDataRepositoryImpl(localDataSource)
    }
}