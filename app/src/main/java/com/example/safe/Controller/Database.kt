package com.andriodproject.safe.Services

import android.util.Log
import com.andriodproject.safe.Model.Post


import com.google.firebase.Firebase


import com.google.firebase.firestore.firestore

class Database<T : Any>(private val collectionName: String) {

    private val firestore =  Firebase.firestore.collection(collectionName)
    var onSucess = false

    fun add(data: T): Boolean {
        firestore.add(data).addOnSuccessListener { documentReference ->
            Log.d("added", "DocumentSnapshot added with ID: ${documentReference.id}")
            onSucess = true
       }
            .addOnFailureListener { e ->
                onSucess = false

            }

        return onSucess
    }

    fun retrieveData(onSuccess: (List<Post>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.get()
            .addOnSuccessListener { querySnapshot ->
                val postList = mutableListOf<Post>()
                for (document in querySnapshot.documents) {
                    val post = document.toObject(Post::class.java)
                    if (post != null) {
                        Log.d("data", post.toString())
                        postList.add(post)
                    } else {
                        Log.d("data", "Post is null")
                    }
                }
                onSuccess(postList)
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error retrieving data", e)
                onFailure(e)
            }
    }

}