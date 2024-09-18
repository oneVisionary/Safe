package com.andriodproject.safe.Common

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.andriodproject.safe.Model.Post


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PostViewModel : ViewModel() {

    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val postList = mutableListOf<Post>()
        response.value = DataState.Loading
        val db = FirebaseFirestore.getInstance()
        db.collection("post")

            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    val post = document.toObject(Post::class.java)

                    // Add the post to the list
                    post?.let {
                        postList.add(it)
                        Log.d("Firestore", "Post: $it")
                    }
                }
                postList.sortByDescending { it.currentDate }
                response.value = DataState.Success(postList)
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents.", exception)
                response.value = DataState.Failure(exception.toString())
            }






    }
}