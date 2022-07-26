package com.kruna1pate1.pictionaryapp.di

import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default + SupervisorJob())
    }

    // Responsible for providing singleton object dependency of
    // Navigation manager
    @Singleton
    @Provides
    fun providesNavigationManager(coroutineScope: CoroutineScope): NavigationManager =
        NavigationManager(coroutineScope)
}