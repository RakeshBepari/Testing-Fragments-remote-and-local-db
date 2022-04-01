package com.example.learningtesting.repositories

import androidx.lifecycle.LiveData
import com.example.learningtesting.data.local.ShoppingDao
import com.example.learningtesting.data.local.ShoppingItem
import com.example.learningtesting.data.remote.PixabayAPI
import com.example.learningtesting.data.remote.responses.ImageResponse
import com.example.learningtesting.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPriceOfShoppingItems(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override fun searchForImage(queryImage: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.getImagefromPixabay(queryImage)
            if (response.isSuccessful){
                response.body()?.let {
                  return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred",null)
            }else {
                Resource.error("Unknown error occurred",null)
            }
        }catch (e:Exception ){
            Resource.error("Network error Check your internet connection", null)
        }
    }

}