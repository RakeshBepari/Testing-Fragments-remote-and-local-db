package com.example.learningtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.learningtesting.HiltTestRunner
import com.example.learningtesting.di.AppModule
import com.example.learningtesting.getOrAwaitValue
import com.example.learningtesting.launchFragmentInHiltContainer
import com.example.learningtesting.ui.ShoppingFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("Test_Db")
    lateinit var shoppingDatabase: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = shoppingDatabase.shoppingDao()
    }

    @After
    fun teardown() {
        shoppingDatabase.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 2, 500f, "url", 1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItmes().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 2, 500f, "url", 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItmes().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)

    }

    @Test
    fun getTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url",null)
        val shoppingItem2 = ShoppingItem("name", 10, 5.5f, "url",null)
        val shoppingItem3 = ShoppingItem("name", 10, 20f, "url",null)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 10 * 5.5f + 10 * 20f)

    }

}