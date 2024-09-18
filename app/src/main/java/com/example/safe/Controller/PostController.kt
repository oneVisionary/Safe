package com.andriodproject.safe.Controller


import android.net.Uri
import com.andriodproject.safe.Model.Post
import com.andriodproject.safe.Services.Database



import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage


import java.time.LocalDateTime
import java.util.UUID

class PostController {
    private val firestore =  Firebase.firestore.collection("post")
    val database = Database<Post>("post")

    fun addPost(post: Post): Boolean {

        var response =  database.add(post)
        return response
    }

    fun uploadFileToFirebase(
        folder: String,
        fileUri: Uri,
        fileType: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val storageRef = Firebase.storage.reference
        val fileRef = storageRef.child("$folder/${UUID.randomUUID()}.$fileType")  // Dynamic folder and file type

        fileRef.putFile(fileUri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

}