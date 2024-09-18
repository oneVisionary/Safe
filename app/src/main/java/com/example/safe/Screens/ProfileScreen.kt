
package com.andriodproject.safe.Screens
import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.andriodproject.safe.Common.convert_timestamp
import com.andriodproject.safe.Controller.PostController
import com.andriodproject.safe.Model.Post
import com.example.safe.ScreenRoutes.ScreenRoutes
import com.example.safe.common.PreferencesManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(
    navHostController: NavHostController
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var LocationName by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val username = remember { PreferencesManager.retrieveData(context, "username") ?: "No Username Found" }
    val userid = remember { PreferencesManager.retrieveData(context, "userid") ?: "No User ID Found" }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var img_Status by remember { mutableStateOf(false) }
    var audiourl by remember { mutableStateOf<String?>(null) }
    var latitude by remember { mutableStateOf<String?>("Loading...") }
    var longitude by remember { mutableStateOf<String?>("Loading...") }
    var titleError by remember { mutableStateOf("") }
    var LocationNameError by remember { mutableStateOf("") }
    var descError by remember { mutableStateOf("") }
    val db = Firebase.firestore
    // Location fetching setup
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                fetchLocation(fusedLocationClient, onLocationReceived = { lat, long ->
                    latitude = lat
                    longitude = long
                })
            } else {
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )


    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imageUri = it
                Log.d("url", imageUri.toString())


            }
        }
    )

    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                PostController().uploadFileToFirebase(
                    folder = "audio",
                    fileUri = it,
                    fileType = "mp3",
                    onSuccess = { downloadUrl ->
                        audiourl = downloadUrl
                        Log.d("download", "Uploaded Audio URL: $downloadUrl")
                    },
                    onFailure = { exception ->
                        Log.d("download error", "Error uploading audio: ${exception.message}")
                    }
                )
            }
        }
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Text("", modifier = Modifier.padding(20.dp).size(30.dp))
            // Title input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                isError = titleError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            OutlinedTextField(
                value = LocationName,
                onValueChange = { LocationName = it },
                label = { Text("Location Name") },
                isError = LocationNameError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            // Description input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                isError = descError.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                maxLines = 5,
                minLines = 3,
                singleLine = false
            )
            Row {
                imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Image Picker Button
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Pick Image")
                }

            }




            Spacer(modifier = Modifier.height(16.dp))

            // Submit Post Button
            Button(
                onClick = {
                    val current = LocalDateTime.now()
                    val getTimeStamp = convert_timestamp().localDateTimeToDate(current)
                    if(imageUri!=null){
                        Log.d("imageUri_data",imageUri.toString())
                        PostController().uploadFileToFirebase(
                            folder = "images",
                            fileUri = imageUri!!,
                            fileType = "jpg",
                            onSuccess = { downloadUrl ->

                                Log.d("post_Data","${title.text}, ${description.text},${LocationName.text} ,${downloadUrl},${img_Status} ${getTimeStamp}")

                                if (title.text.isEmpty()) {
                                    titleError = "Title is required"
                                }
                                if (description.text.isEmpty()) {
                                    descError = "Description is required"
                                }
                                if (titleError.isEmpty() && descError.isEmpty()) {
                                    if (latitude != null  && downloadUrl !=null) {
                                        val postData = Post(userid,username,title.text,description.text, downloadUrl!!,LocationName.text,
                                            latitude!!,longitude!!, currentDate = current.toString()
                                        )


                                        val response = PostController().addPost(postData)
                                        Toast.makeText(context, "Unsafe Area Posted", Toast.LENGTH_SHORT).show()
                                        navHostController.navigate(ScreenRoutes.HomeScreen.route)

                                    }


                                }



                            },
                            onFailure = { exception ->
                                Log.d("download error", "Error uploading file: ${exception.message}")
                            }
                        )

                    }



                },
















                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                Text("Add Danger Spot")
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (String, String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocationReceived(location.latitude.toString(), location.longitude.toString())
        } else {
            onLocationReceived("Location not available", "Location not available")
        }
    }.addOnFailureListener {
        Log.e("Location Error", "Failed to get location", it)
    }
}
