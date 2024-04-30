package com.example.test.network


import android.content.Context
import com.example.test.repository.Cache
import com.example.test.repository.Repository
import com.example.test.repository.RepositoryImpl
import com.example.test.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context)
    }
    @Provides
    @Singleton
    fun provideRepository(service: Service, cache: Cache ): Repository {
        return RepositoryImpl(service, cache)
    }
}