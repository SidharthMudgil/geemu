package com.sidharth.geemu.di

import com.sidharth.geemu.domain.repository.GameRepository
import com.sidharth.geemu.domain.repository.UserDataRepository
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCase
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCaseImpl
import com.sidharth.geemu.domain.usecase.detail.GetDetailsUseCase
import com.sidharth.geemu.domain.usecase.detail.GetDetailsUseCaseImpl
import com.sidharth.geemu.domain.usecase.game.GameUseCase
import com.sidharth.geemu.domain.usecase.game.GameUseCaseImpl
import com.sidharth.geemu.domain.usecase.tag.TagUseCase
import com.sidharth.geemu.domain.usecase.tag.TagUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetDetailsUseCase(
        gameRepository: GameRepository
    ): GetDetailsUseCase {
        return GetDetailsUseCaseImpl(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGameUseCase(
        gameRepository: GameRepository
    ): GameUseCase {
        return GameUseCaseImpl(gameRepository)
    }

    @Provides
    @Singleton
    fun provideCollectionUseCase(
        userDataRepository: UserDataRepository
    ): CollectionUseCase {
        return CollectionUseCaseImpl(userDataRepository)
    }

    @Provides
    @Singleton
    fun provideTagUseCase(
        userDataRepository: UserDataRepository
    ): TagUseCase {
        return  TagUseCaseImpl(userDataRepository)
    }
}