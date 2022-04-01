package com.example.learningtesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{

    /**
     * We should not declare the ResourceComparer here as both the test cases depend on a single variable which is not good
     * Cause suppose if the variable contains a count in and if a test does operations on the count and then the second test
     * does operations on the same count which is modified by the first test which is not good and leads to flaky test.
     * So the one test should not depend on other or the outcome of other test.
     */
    //    private val resourceComparer = ResourceComparer()

    private lateinit var resourceComparer : ResourceComparer

    @Before // runs before every test case
    fun setup() {
        val resourceComparer = ResourceComparer()
    }

    @After // runs after every test case
    fun teardown(){
    // Garbage collector automatically destroys the object so we don't have to destroy it here for now but in case of a room
    // database we close the database here
    }

    @Test
    fun stringResourceSameAsGivenString_ReturnsTrue() {
        // resourceComparer = ResourceComparer() ----- We don't initialize ResourceComparer here because for every test that needs
        // ResourceComparer we have to initialize it and it will lead to lot of boilerplate code so we do this in fun setup()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"LearningTesting")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenString_ReturnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"LearningTesting")

        assertThat(result).isTrue()
    }
}