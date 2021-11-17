package com.bernardo.pelicula.di

import android.content.Context
import androidx.room.Room
import com.bernardo.pelicula.AppDatabase
import com.bernardo.pelicula.BuildConfig
import com.bernardo.pelicula.domain.WebService
import com.bernardo.pelicula.utils.AppConstants.BASE_URL
import com.bernardo.pelicula.utils.AppConstants.TABLE_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            TABLE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideMoivesDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val url =
            chain.request().url.newBuilder()
                .build()
        val request = chain.request()
            .newBuilder()
            .header("Content-Type", "application/json;charset=utf-8")
            .header("Authorization", "Bearer "+ BuildConfig.TMDB_TOKEN)
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideApiClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(apiClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(apiClient)
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)

}