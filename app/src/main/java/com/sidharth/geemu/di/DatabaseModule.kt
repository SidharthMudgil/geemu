package com.sidharth.geemu.di

import android.content.Context
import androidx.room.Room
import com.sidharth.geemu.core.constant.Constants.APP_DATABASE_NAME
import com.sidharth.geemu.data.local.AppDatabase
import com.sidharth.geemu.data.local.LocalDataSource
import com.sidharth.geemu.data.local.UserDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDataDao(
        appDatabase: AppDatabase
    ): UserDataDao {
        return appDatabase.getUserDataDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        userDataDao: UserDataDao
    ): LocalDataSource {
        return LocalDataSource(userDataDao)
    }
}