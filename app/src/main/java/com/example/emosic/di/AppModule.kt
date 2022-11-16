package com.example.emosic.di

import com.example.emosic.repository.LikedSongsRepository
import com.example.emosic.repository.LikedSongsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideLikedSongsRepository() : LikedSongsRepository = LikedSongsRepositoryImpl()
}