package com.example.learningtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.learningtesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest // For unit tests
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    /**
     * This rule is used to run the code inside the test case sequentially one after another
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("Test_Db")
    lateinit var shoppingDatabase: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        /**
         * We don't initiate the shoppingDatabase like this cause instantiating various variable in various test class in the
         * @Before function can get messy so we can instead use DI for Test case and Inject have common place for instantiating
         * all the variables and later we can inject it.
         *
         * But the thing to note is the injection works only on instrumented tests i.e. inside the android test directory
         */
//        shoppingDatabase = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            ShoppingDatabase::class.java
//        ).allowMainThreadQueries().build()

        hiltRule.inject()
        dao = shoppingDatabase.shoppingDao()
    }

    @After
    fun teardown() {
        shoppingDatabase.close()
    }

    /**
     * runBlockingTest is runBlocking for test which is used to run a coroutine on the main thread through which we call
     * suspend functions
     */
    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 2, 500f, "url", 1)
        dao.insertShoppingItem(shoppingItem)

        //observeAllShoppingItems() returns a LiveData not a list and LiveData is asynchronous but using the extension function
        // getOrAwaitValue() we can get the List
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 2, 500f, "url", 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

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