package com.example.safe.Screens


import androidx.navigation.NavController

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
;
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
;
import androidx.compose.ui.unit.sp
import com.example.safe.R
import com.example.safe.ScreenRoutes.ScreenRoutes
import com.example.safe.common.PreferencesManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val db = Firebase.firestore
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    var message by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_700)),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo" ,modifier = Modifier.size(150.dp))
        Text(text = "Login", fontSize = 25.sp, fontWeight = FontWeight.Bold , color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Card (modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(

                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

                )

            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        db.collection("users")
                            .whereEqualTo("email", email.text)
                            .whereEqualTo("password", password.text)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (querySnapshot.isEmpty) {
                                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                                } else {
                                    for (document in querySnapshot.documents) {
                                        val documentId = document.id
                                        val username = document["username"] as? String ?: "Unknown User"
                                        PreferencesManager.storeData(context, "username", username)
                                        PreferencesManager.storeData(context, "userid", documentId)
                                        navController.navigate(ScreenRoutes.HomeNav.route)
                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(context,"Something went wrong ${exception.message}", Toast.LENGTH_SHORT).show()
                            }



                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),  shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Login")
                }
                if (showDialog) {




                    email = TextFieldValue("")
                    password = TextFieldValue("")

                }
                Button( modifier = Modifier.padding(top = 19.dp), onClick = {
                    navController.navigate(route = "signup_screen")
                }  ,     colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),  shape = MaterialTheme.shapes.medium) {

                    Text(text = "Create an new account")
                }

            }

        }

    }
}

