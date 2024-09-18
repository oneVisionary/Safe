package com.andriodproject.safe.Model

import java.util.Date

data class Post(
    val userid: String = "",
    val name: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val location:  String = "",
    val latitude: String = "",
    val longitude: String = "",
    val currentDate: String = ""
)