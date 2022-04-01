package com.example.learningtesting

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * To test Fragments we need fragment scenario where a fragment is launched in an empty activity but the problem
 * is that the activity is not annotated with @AndroidEntryPoint so the DI does not work for the test in that case
 * so to make it work we create our own custom activity HiltTestActivity to which we can later attach the fragment
 * and can have the functionalities of DI in the Fragment in Test cases
 */

@AndroidEntryPoint
class HiltTestActivity: AppCompatActivity()