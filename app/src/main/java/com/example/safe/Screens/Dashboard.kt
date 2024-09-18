package com.andriodproject.safe.Screens
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.andriodproject.safe.Common.DataState
import com.andriodproject.safe.Common.PostViewModel
import com.andriodproject.safe.Model.Post
import com.example.safe.R
import com.example.safe.ScreenRoutes.ScreenRoutes


@Composable
fun Dashboard(
    navHostController: NavHostController,

) {
    val viewModel: PostViewModel = viewModel()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box(modifier = Modifier.padding(top = 25.dp)){
                SetData(viewModel,navHostController)
            }

        }
    }
}
@Composable
fun SetData(viewModel: PostViewModel, navHostController: NavHostController) {
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DataState.Success -> {
            ShowLazyList(result.data, navHostController = navHostController)
        }
        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    
                )
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                    
                )
            }
        }
    }
}

@Composable
fun ShowLazyList(foods: MutableList<Post>, navHostController: NavHostController) {
    LazyColumn(modifier = Modifier.padding(top = 25.dp)) {
        items(foods) { food ->
            CardItem(food, navHostController =navHostController )
        }
    }
}
@Composable
fun CardItem(post: Post, navHostController: NavHostController) {
    var iconDrawableId: Int = R.drawable.map
    var iconComment: Int = R.drawable.comment
    Log.d("postinfo_data",post.toString())
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(20.dp),

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image on the left side
            AsyncImage(
                model = post.image,
                contentDescription = "Post image",
                modifier = Modifier
                    .size(100.dp) // Adjust the size of the image
                    .padding(8.dp), // Add some padding around the image
                contentScale = ContentScale.Crop // Crop the image to fill its bounds
            )

            // Content on the right side
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text( text = post.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 2, // Limit to 2 lines and add ellipsis if it's too long
                    overflow = TextOverflow.Ellipsis)

                Text("Post By: ${post.userid}")
                Spacer(modifier = Modifier.height(16.dp))

                Text(post.currentDate,    style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                ),)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { /* TODO: Handle icon click */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconComment),
                            contentDescription = "Comment"
                        )
                    }
                    IconButton(onClick = {
                        val latitude = post.latitude.toDoubleOrNull() ?: 0.0
                        val longitude = post.longitude.toDoubleOrNull() ?: 0.0
                        // Navigate to detail page
                        navHostController.navigate(ScreenRoutes.DetailScreen.createRoute(latitude, longitude))
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconDrawableId),
                            contentDescription = "Map"
                        )
                    }
                    IconButton(onClick = {
                        val userid = post.userid


                        navHostController.navigate(ScreenRoutes.ViewMyPost.createRoute(userid))
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconDrawableId),
                            contentDescription = "Map"
                        )
                    }

                }
            }
        }
    }
}
