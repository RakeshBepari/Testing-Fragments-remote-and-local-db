package com.example.learningtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("Select * from shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("Select SUM(price * amount) from shopping_items")
    fun observeTotalPrice(): LiveData<Float>

}