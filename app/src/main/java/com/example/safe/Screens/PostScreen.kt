package com.safe.safe.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.safe.R
import com.example.safe.common.PreferencesManager


@Composable
fun ProfileScreen(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val userid = remember { PreferencesManager.retrieveData(context, "userid") ?: "No User ID Found" }
    val username = remember { PreferencesManager.retrieveData(context, "username") ?: "No Username Found" }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = "View Profile ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                modifier = Modifier.padding(22.dp),
                textAlign = TextAlign.Center,
            )
            Image(
                painter = painterResource(id = R.drawable.userprofile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(200.dp) // Adjust the size of the image
                    .padding(bottom = 28.dp) // Padding below the image
            )



            Text(
                text = "$username ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "$userid",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
            )
        }
    }

}