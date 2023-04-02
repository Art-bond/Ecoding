package com.d3st.e_coding.ui.camera

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CameraAfterScreen(
    modifier: Modifier = Modifier,
    imageFile: Uri,
    onClickNext:()-> Unit,

    ){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = imageFile,
            contentDescription = null
        )
        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                Log.i("oldRepublic", "navigate to Recognize")
                onClickNext
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Done,
                    contentDescription = "Go To Recognize",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )

            })
    }
}