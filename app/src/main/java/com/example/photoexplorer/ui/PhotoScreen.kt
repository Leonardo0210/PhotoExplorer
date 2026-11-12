
package com.example.photoexplorer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.photoexplorer.model.Photo
import com.example.photoexplorer.viewmodel.PhotoUiState
import com.example.photoexplorer.viewmodel.PhotoViewModel

@Composable
fun PhotoScreen(
    photoViewModel: PhotoViewModel = viewModel()
) {
    val state by photoViewModel.uiState.collectAsState()

    val backgroundColor = Color(0xFF101820)
    val accentColor = Color(0xFF00C2A8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        Text(
            text = "PhotoExplorer",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Descubre fotografías del mundo",
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (val result = state) {

            is PhotoUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = "https://picsum.photos/id/10/400/300",
                        contentDescription = "Cargando fotografías",
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    CircularProgressIndicator(color = accentColor)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Buscando fotografías...",
                        color = Color.White
                    )
                }
            }

            is PhotoUiState.Success -> {
                Text(
                    text = "${result.photos.size} fotografías encontradas",
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(result.photos) { photo ->
                        PhotoCard(photo)
                    }
                }
            }

            is PhotoUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = "https://picsum.photos/id/237/400/300",
                        contentDescription = "Error de conexión",
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "¡Ups! Algo salió mal",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        result.message,
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { photoViewModel.loadPhotos() }
                    ) {
                        Text("Intentar nuevamente")
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoCard(photo: Photo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF263640)
        )
    ) {
        Column {

            AsyncImage(
                model = photo.download_url,
                contentDescription = "Fotografía de ${photo.author}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = photo.author,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Text(
                    text = "ID: ${photo.id}",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
