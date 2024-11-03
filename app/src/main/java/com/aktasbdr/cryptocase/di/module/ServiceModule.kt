package com.aktasbdr.cryptocase.di.module

import com.aktasbdr.cryptocase.data.service.CommonService
import com.aktasbdr.cryptocase.data.service.GraphService
import com.aktasbdr.cryptocase.di.qualifier.CommonApi
import com.aktasbdr.cryptocase.di.qualifier.GraphApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideCommonService(
        @CommonApi retrofit: Retrofit
    ): CommonService {
        println("ServiceModule - Creating CommonService") // Debug log
        return retrofit.create(CommonService::class.java)
    }

    @Provides
    @Singleton
    fun provideGraphService(
        @GraphApi retrofit: Retrofit
    ): GraphService {
        println("ServiceModule - Creating GraphService") // Debug log
        return retrofit.create(GraphService::class.java)
    }
}
