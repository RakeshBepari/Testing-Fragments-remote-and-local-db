package com.example.learningtesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.learningtesting.R
import com.example.learningtesting.data.local.ShoppingDao
import com.example.learningtesting.data.local.ShoppingDatabase
import com.example.learningtesting.data.remote.PixabayAPI
import com.example.learningtesting.other.Constants.BASE_URL
import com.example.learningtesting.other.Constants.DATABASE_NAME
import com.example.learningtesting.repositories.DefaultShoppingRepository
import com.example.learningtesting.repositories.ShoppingRepository
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
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

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

    @Provides
    @Singleton
    fun providesDefaultRepository(
        dao: ShoppingDao,
        api:PixabayAPI
    ) = DefaultShoppingRepository(dao,api) as ShoppingRepository

}

