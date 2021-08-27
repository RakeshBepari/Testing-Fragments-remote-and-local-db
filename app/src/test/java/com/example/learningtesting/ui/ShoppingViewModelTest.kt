package com.example.learningtesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.learningtesting.MainCoroutineRule
import com.example.learningtesting.other.Constants.MAX_NAME_LENGTH
import com.example.learningtesting.other.Constants.MAX_PRICE_LENGTH
import com.example.learningtesting.other.Status
import com.example.learningtesting.repositories.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var shoppingViewModel: ShoppingViewModel

    @Before
    fun setup() {
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`() {
        shoppingViewModel.insertShoppingItemValidation("name", "", "5.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for (i in 1..MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItemValidation(string, "5", "5.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for (i in 1..MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        shoppingViewModel.insertShoppingItemValidation("name", "5", string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {

        shoppingViewModel.insertShoppingItemValidation("name", "999999999999999999999", "5.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun  `insert shopping item with valid inputs, returns success`(){

        shoppingViewModel.insertShoppingItemValidation("name","5", "5.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun  `inserted shopping item , sets curImageUrl to empty string`(){

        shoppingViewModel.insertShoppingItemValidation("name","5", "5.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

        val currImageUrl = shoppingViewModel.curImageUrl.getOrAwaitValueTest()
        assertThat(currImageUrl).isEmpty()
    }

    @Test
    fun `fun setCurImageUrl sets `(){
        shoppingViewModel.setCurImageUrl("url")

        val value = shoppingViewModel.curImageUrl.getOrAwaitValueTest()

        assertThat(value).isEqualTo("url")
    }

}