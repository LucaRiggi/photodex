package com.example.photodex.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photodex.viewmodel.FavouriteViewModel

@Composable
fun PictureDetail(
    id: Int,
    navBack: () -> Unit,
    viewModel: FavouriteViewModel = viewModel()
) {
    val picture by viewModel.getPicture(id).collectAsState(initial = null)
    if (picture == null) {
        // Show a placeholder while loading
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        val currentPicture = picture!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                AsyncImage(
                    model = currentPicture.imageLink,
                    contentDescription = currentPicture.name,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {}
                ) {
                    Text("Download")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Download")
                }
            }

            Button(
                onClick = {viewModel.unfavourite(picture!!); navBack()}
            ) {
                Text("Unfavourite")
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.Favorite, contentDescription = "Unfavourite")
            }

        }
    }
}