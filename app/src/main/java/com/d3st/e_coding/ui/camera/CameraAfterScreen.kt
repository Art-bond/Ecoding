package com.d3st.e_coding.ui.camera

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Crop
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.moyuru.cropify.Cropify
import io.moyuru.cropify.rememberCropifyState

/**
 * Screen shows captured picture from camera
 *
 * @param modifier Modifier screen
 * @param imageFile uri captured picture
 * @param onImageCropped return success cropping result
 * @param onFailedToLoadImage return failure cropping result
 */
@Composable
fun SnapshotScreen(
    modifier: Modifier = Modifier,
    imageFile: Uri,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedToLoadImage: () -> Unit,
    onClickNext: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier.fillMaxSize()
    ) {
        CameraImagePostView(
            imageUri = imageFile,
            onClickNext = onClickNext,
            onImageCropped = onImageCropped,
            onFailedToLoadImage = onFailedToLoadImage
        )
    }
}

@Composable
fun CameraImagePostView(
    imageUri: Uri,
    onClickNext: () -> Unit,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedToLoadImage: () -> Unit,
) {

    var cropState by remember { mutableStateOf(false) }
    if (cropState) {
        CroppingImage(
            imageUri = imageUri,
            onImageCropped = onImageCropped,
            onFailedToLoadImage = onFailedToLoadImage
        )
    } else {
        PostCameraView(
            imageUri = imageUri,
            onClickNext = onClickNext,
            onCropClick = { cropState = true })

    }
}

@Composable
fun PostCameraView(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    onCropClick: () -> Unit,
    onClickNext: () -> Unit,
) {
    AsyncImage(
        modifier = modifier,
        model = imageUri,
        contentDescription = null
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = modifier.weight(1f),
                onClick = onCropClick,
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Crop,
                        contentDescription = "Crop",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                    )
                })
            IconButton(
                modifier = modifier.weight(1f),
                onClick = {
                    onClickNext()
                },
                content = {
                    Icon(
                        imageVector = Icons.Sharp.Done,
                        contentDescription = "Go to Recognize",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(1.dp)
                            .border(1.dp, Color.White, CircleShape)
                    )
                })
        }
    }

}

@Composable
fun CroppingImage(
    imageUri: Uri,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedToLoadImage: () -> Unit,
) {
    val state = rememberCropifyState()

    Cropify(
        uri = imageUri,
        state = state,
        onImageCropped = onImageCropped,
        onFailedToLoadImage = onFailedToLoadImage
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                state.crop()
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "Crop",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            })
    }
}

@Preview
@Composable
fun PreviewPostCameraScreen() {
    SnapshotScreen(imageFile = Uri.EMPTY, onImageCropped = {}, onFailedToLoadImage = { }) {
    }
}