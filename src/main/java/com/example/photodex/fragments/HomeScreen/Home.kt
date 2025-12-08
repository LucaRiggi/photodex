package com.example.photodex.fragments.HomeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photodex.data.picture.Picture
import com.example.photodex.fragments.GalleryViewModel
import com.example.photodex.viewmodel.FavouriteViewModel

/*
 *
 * !!!!!!!!!!!!! GEMINI ALERT !!!!!!!!!!!!
 *
 * Gemini 3 (Thinking & Reasoning) was used to assist on fixing issues in this file
 * it was also used to do research on up to date syntax for jetpack compose.
 *
 * Research was used because i didn't feel like reading the docs.
 *
 */
@Composable
fun Home(
    viewModel: GalleryViewModel = viewModel(),
    favouriteViewModel: FavouriteViewModel = viewModel(),
    onNavigate: (String) -> Unit
) {
    val pics by viewModel.images.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("Search photos...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.searchImages("")
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchImages(searchQuery)
                    focusManager.clearFocus()
                }
            ),
            singleLine = true
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items = pics, key = { it.id }) { pic ->
                Picture(
                    url = pic.urls.regular,
                    desc = pic.alt_description ?: "Photo",
                    id = pic.id,
                    onNavigate = onNavigate,
                    onFavoriteClick = {
                        val newFavorite = Picture(
                            id = 0,
                            name = pic.alt_description ?: "Unknown",
                            imageLink = pic.urls.regular
                        )

                        favouriteViewModel.favourite(newFavorite)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Picture(
    url: String,
    desc: String,
    id: String,
    isFavorite: Boolean = false,
    onNavigate: (String) -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onNavigate(id) }
    ) {
        Column {
            AsyncImage(
                model = url,
                contentDescription = desc,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { onFavoriteClick() }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}