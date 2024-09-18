package com.example.safe.Screens


import androidx.navigation.NavController
import android.widget.Space
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
;
import androidx.compose.ui.unit.sp
import com.example.safe.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var contact by remember { mutableStateOf(TextFieldValue("")) }
    val db = Firebase.firestore
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_700)),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        var showDialog by remember { mutableStateOf(false) }

        var message by remember { mutableStateOf("") }
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo" ,modifier = Modifier.size(150.dp))
        Text(text = "Sign Up", fontSize = 25.sp, fontWeight = FontWeight.Bold , color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Card (modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(

                value = username,
                onValueChange = { username = it },
                label = { Text("username") },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(

                value = contact,
                onValueChange = { contact = it },
                label = { Text("contact") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))



            Row {
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        val user = hashMapOf(
                            "username" to username.text.trim(),
                            "email" to email.text.trim(),
                            "password" to password.text.trim(),
                            "contact" to contact.text.trim()
                        )


                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d("added", "DocumentSnapshot added with ID: ${documentReference.id}")
                                message = "Added new account"

                                showDialog = true
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context,"Something went wrong ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        Log.d("SignUp", "Username: ${username.text}")
                    } ,colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),  shape = MaterialTheme.shapes.medium) {
                    Text("Sign Up")

                }
                if (showDialog) {


                    Toast.makeText(context,"Account Successfully Created", Toast.LENGTH_SHORT).show()
                    username = TextFieldValue("")
                    email = TextFieldValue("")
                    password = TextFieldValue("")
                    contact = TextFieldValue("")
                }
                Button( modifier = Modifier.padding(top = 19.dp), onClick = {
                    navController.navigate(route = "login_route")
                }  ,     colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),  shape = MaterialTheme.shapes.medium) {

                    Text(text = "Already Have an account")
                }

            }
        }

    }
}

