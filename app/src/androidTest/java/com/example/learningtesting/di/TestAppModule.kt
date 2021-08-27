package com.example.learningtesting.di

import android.content.Context
import androidx.room.Room
import com.example.learningtesting.data.local.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("Test_Db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room
        .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}