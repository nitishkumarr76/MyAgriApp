package com.example.myagri.features.profile


import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.myagri.features.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myagri.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(innerNavController: NavController,
                  rootNavController: NavController) {

    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel() }

    val user = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        innerNavController.popBackStack()
                    }) {
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
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )

                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .background(Color.White, CircleShape)
                            .padding(4.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = user?.displayName ?: "User",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = user?.email ?: "No Email",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Text(
                    text = user?.phoneNumber ?: "No Phone",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column {

                    ProfileItem("Personal Information", Icons.Default.Person)
                    ProfileItem("Language", Icons.Default.Language, "English")
                    ProfileItem("Voice Settings", Icons.Default.Mic)
                    ProfileItem("Notifications", Icons.Default.Notifications)
                    ProfileItem("Help & Support", Icons.Default.Help)
                    ProfileItem("About Us", Icons.Default.Info)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    authViewModel.logout(context) {
                        rootNavController.navigate("welcome") {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(
                    text = "Logout",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ProfileItem(
    title: String,
    icon: ImageVector,
    trailing: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (trailing != null) {
            Text(
                text = trailing,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}