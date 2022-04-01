package com.example.learningtesting

import android.content.Context

class ResourceComparer {

    fun isEqual(context: Context, resId: Int, string: String) =
        context.getString(resId) == string
}