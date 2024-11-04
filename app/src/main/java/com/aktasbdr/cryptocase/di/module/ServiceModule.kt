package com.aktasbdr.cryptocase.di.module

import com.aktasbdr.cryptocase.feature_crypto.data.service.CommonService
import com.aktasbdr.cryptocase.feature_crypto.data.service.GraphService
import com.aktasbdr.cryptocase.di.qualifier.CommonApi
import com.aktasbdr.cryptocase.di.qualifier.GraphApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideCommonService(
        @CommonApi retrofit: Retrofit
    ): CommonService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideGraphService(
        @GraphApi retrofit: Retrofit
    ): GraphService {
        return retrofit.create()
    }
}
