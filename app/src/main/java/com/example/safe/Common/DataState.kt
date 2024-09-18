package com.andriodproject.safe.Common

import com.andriodproject.safe.Model.Post


sealed class DataState {
    class Success(val data: MutableList<Post>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}