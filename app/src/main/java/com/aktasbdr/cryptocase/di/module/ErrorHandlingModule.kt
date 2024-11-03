package com.aktasbdr.cryptocase.di.module

import android.content.Context
import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.data.exception.HandleException
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlingModule {
    @Provides
    @Singleton
    fun provideHandleException(
        gson: Gson,
        @ApplicationContext context: Context
    ): HandleException {
        return HandleException(gson, context)
    }

    @Provides
    @Singleton
    fun provideSafeApiCall(
        handleException: HandleException
    ): SafeApiCall {
        return SafeApiCall(handleException)
    }
}