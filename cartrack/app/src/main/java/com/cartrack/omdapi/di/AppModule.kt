package com.cartrack.omdapi.di

import android.content.Context
import com.cartrack.omdapi.Constants
import com.cartrack.omdapi.data.local.LocalDatabase
import com.cartrack.omdapi.data.local.MediaDao
import com.cartrack.omdapi.data.remote.MediaApiService
import com.cartrack.omdapi.data.remote.MediaRemoteDataSource
import com.cartrack.omdapi.data.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = setupRetrofit()

    @Provides
    fun provideMediaApiService(retrofit: Retrofit): MediaApiService =
        retrofit.create(MediaApiService::class.java)

    @Singleton
    @Provides
    fun provideMediaRemoteDataSource(mediaApiService: MediaApiService) =
        MediaRemoteDataSource(mediaApiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = LocalDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMediaDao(db: LocalDatabase) = db.mediaDao()

    @Singleton
    @Provides
    fun provideMediaRepository(
        mediaRemoteDataSource: MediaRemoteDataSource,
        localDataSource: MediaDao
    ) = MediaRepository(mediaRemoteDataSource, localDataSource)

    private fun setupRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}