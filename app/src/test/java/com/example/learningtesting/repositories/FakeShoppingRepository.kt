package com.example.learningtesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningtesting.data.local.ShoppingItem
import com.example.learningtesting.data.remote.responses.ImageResponse
import com.example.learningtesting.other.Resource


class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float{
        return shoppingItems.sumOf{
            it.price.toDouble() * it.amount.toDouble()
        }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observeTotalPriceOfShoppingItems(): LiveData<Float> {
        return observableTotalPrice
    }

    override fun getImage(queryImage: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resource.error("Error",null)
        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}