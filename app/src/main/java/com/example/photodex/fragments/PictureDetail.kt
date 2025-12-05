package com.example.photodex.fragments

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.AsyncImage
import com.example.photodex.viewmodel.FavouriteViewModel
import com.example.photodex.workers.DownloadWorkManager

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
        var context = LocalContext.current

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
                    onClick = {
                        /**
                         * SOURCE: https://www.droidcon.com/2022/03/10/step-by-step-guide-to-download-files-with-workmanager/
                         * and classwork
                         */
                        val data = Data.Builder()
                        data.apply {
                            putString(DownloadWorkManager.FileParams.KEY_FILE_NAME, "${currentPicture.name}.jpg")
                            putString(DownloadWorkManager.FileParams.KEY_FILE_URL, currentPicture.imageLink)
                            putString(DownloadWorkManager.FileParams.KEY_FILE_TYPE, "jpg")
                        }

                        val syncRequest = OneTimeWorkRequestBuilder<DownloadWorkManager>()
                            .setInputData(data.build())
                            .setConstraints(Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)

                                .build())
                            .build()

                        WorkManager.getInstance(context).enqueueUniqueWork(
                            "file_sync_retry",
                            ExistingWorkPolicy.REPLACE,
                            syncRequest
                        )

                        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show()
                    }
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