package com.example.learningtesting.di

import android.content.Context
import androidx.room.Room
import com.example.learningtesting.data.local.ShoppingDatabase
import com.example.learningtesting.data.remote.PixabayAPI
import com.example.learningtesting.other.Constants.BASE_URL
import com.example.learningtesting.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesShoppingDao(
        database: ShoppingDatabase
    ) = database.shoppingDao()

    @Provides
    @Singleton
    fun providesRetrofitInstance(): PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }


}

