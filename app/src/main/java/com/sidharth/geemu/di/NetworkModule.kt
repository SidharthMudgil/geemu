package com.sidharth.geemu.di

import com.sidharth.geemu.core.constant.Constants.BASE_URL
import com.sidharth.geemu.data.remote.service.RAWGService
import com.sidharth.geemu.data.remote.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor {
                    val url = it.request().url.newBuilder()
                        .addQueryParameter("key", "BuildConfig.API_KEY")
                        .build()

                    val request = it.request().newBuilder().url(url).build()
                    it.proceed(request)
                }.build()
            ).build()
    }

    @Provides
    @Singleton
    fun provideRawgService(
        retrofit: Retrofit
    ): RAWGService {
        return retrofit.create(RAWGService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        rawgService: RAWGService
    ): RemoteDataSource {
        return RemoteDataSource(rawgService)
    }
}