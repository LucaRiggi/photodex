package com.example.photodex.fragments.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Home() {
    Column(
        modifier = Modifier.fillMaxSize()
        .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("What's up? We're running low on API keys so enjoy the spectacular picture of a monkey")
        // async image is third party library
        AsyncImage(
            model = "https://cdn.pixabay.com/photo/2017/03/13/10/31/monkey-2139295_960_720.jpg",
            contentDescription = "Monkey",
            modifier = Modifier.fillMaxWidth()
        )
    }
}