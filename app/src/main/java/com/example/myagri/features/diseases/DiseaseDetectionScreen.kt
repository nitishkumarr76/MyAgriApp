package com.example.myagri.features.diseases

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myagri.R
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetectionScreen(innerNavController: NavController) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showOptions by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            imageUri = saveBitmapToCache(context, it)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val cameraGranted = permissions[Manifest.permission.CAMERA] == true

        if (cameraGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Disease Detection",
                    fontWeight = FontWeight.SemiBold
                ) },
                navigationIcon = {
                    IconButton(onClick = { innerNavController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            if (showOptions) {
                ModalBottomSheet(onDismissRequest = { showOptions = false }) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "Select Image", fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            "Take Photo",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    permissionLauncher.launch(
                                        arrayOf(Manifest.permission.CAMERA)
                                    )
                                    showOptions = false
                                }
                                .padding(12.dp)
                        )

                        Text(
                            "Choose from Gallery",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                    showOptions = false
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }

            // Upload Image Card
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                        RoundedCornerShape(16.dp)
                    )
                    .clickable { showOptions = true },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (imageUri == null) {

                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("Upload Image",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            "Tap to choose from gallery",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            "or take a photo",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                    } else {

                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Result Button
            Button(
                onClick = {
                    innerNavController.navigate("result")
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("See Result",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Recent Scans",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
                    .clickable{
                        innerNavController.navigate("result")
                    },
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ricesq),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Rice",
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            "Early Bright",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            "20 May 2025",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Icon(Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "captured_image.jpg")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}