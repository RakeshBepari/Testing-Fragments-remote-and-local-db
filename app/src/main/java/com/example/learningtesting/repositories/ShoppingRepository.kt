package com.example.learningtesting.repositories

import androidx.lifecycle.LiveData
import com.example.learningtesting.data.local.ShoppingItem
import com.example.learningtesting.data.remote.responses.ImageResponse
import com.example.learningtesting.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    fun observeTotalPriceOfShoppingItems(): LiveData<Float>

    fun getImage(queryImage:String): Resource<ImageResponse>
}