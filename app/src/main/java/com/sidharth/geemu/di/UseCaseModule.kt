package com.sidharth.geemu.di

import com.sidharth.geemu.domain.repository.GameRepository
import com.sidharth.geemu.domain.repository.UserDataRepository
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCase
import com.sidharth.geemu.domain.usecase.collection.CollectionUseCaseImpl
import com.sidharth.geemu.domain.usecase.detail.GetCreatorDetailsUseCase
import com.sidharth.geemu.domain.usecase.detail.GetCreatorDetailsUseCaseImpl
import com.sidharth.geemu.domain.usecase.detail.GetGameDetailsUseCase
import com.sidharth.geemu.domain.usecase.detail.GetGameDetailsUseCaseImpl
import com.sidharth.geemu.domain.usecase.game.GetGameUseCase
import com.sidharth.geemu.domain.usecase.game.GetGameUseCaseImpl
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
    fun provideGetGameDetailsUseCase(
        gameRepository: GameRepository
    ): GetGameDetailsUseCase {
        return GetGameDetailsUseCaseImpl(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGetCreatorDetailsUseCase(
        gameRepository: GameRepository
    ): GetCreatorDetailsUseCase {
        return GetCreatorDetailsUseCaseImpl(gameRepository)
    }

    @Provides
    @Singleton
    fun provideGameUseCase(
        gameRepository: GameRepository
    ): GetGameUseCase {
        return GetGameUseCaseImpl(gameRepository)
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
        return TagUseCaseImpl(userDataRepository)
    }
}