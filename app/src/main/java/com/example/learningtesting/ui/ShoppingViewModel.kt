package com.example.learningtesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningtesting.data.local.ShoppingItem
import com.example.learningtesting.data.remote.responses.ImageResponse
import com.example.learningtesting.other.Constants
import com.example.learningtesting.other.Event
import com.example.learningtesting.other.Resource
import com.example.learningtesting.repositories.ShoppingRepository
import kotlinx.coroutines.launch
import kotlin.Exception

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItem()

    val totalPrice = repository.observeTotalPriceOfShoppingItems()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItemValidation(name: String, amountString: String, priceString: String) {
        if ( name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error(
                "The fields must not be empty",
                null
            )))
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus
                .postValue(Event(Resource.error(
                    "The name of the item must not exceed ${Constants.MAX_NAME_LENGTH} characters",
                    null
                )))
            return
        }
        if (priceString.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus
                .postValue(Event(Resource.error(
                    "The price of the item must not exceed ${Constants.MAX_PRICE_LENGTH} characters",
                    null
                )))
            return
        }
        val amount = try {
            amountString.toInt()
        }catch (e:Exception){
            _insertShoppingItemStatus
                .postValue(Event(Resource.error(
                    "Please enter a valid amount",
                    null
                )))
            return
        }

        val shoppingItem = ShoppingItem(name,amount,priceString.toFloat(),_curImageUrl.value ?: "",null)
        insertShoppingItem(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus
            .postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(queryImage: String) {
        if (queryImage.isEmpty()) {
            return
        }
        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchForImage(queryImage)
            _images.value = Event(response)
        }

    }


}