package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SilvanusApp()
        }
    }
}



val Green = Color(0xFF2E7D32)
val LightGreen = Color(0xFFE8F5E9)
val DarkGreen = Color(0xFF1B5E20)
val Background = Color(0xFFF8FAF7)
val TextDark = Color(0xFF172117)
val TextGrey = Color(0xFF667066)



enum class CustomIconType {
    BACK, AGRICULTURE, PERSON, BADGE, PHONE, LOCATION,
    GPS, WALK, MAP, CHEVRON_RIGHT, SAVE, CLOUD, VERIFIED, CHECK
}

@Composable
fun CustomIcon(
    type: CustomIconType,
    tint: Color = TextDark,
    modifier: Modifier = Modifier.size(24.dp)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        when (type) {
            CustomIconType.BACK -> {
                val path = Path().apply {
                    moveTo(w * 0.8f, h * 0.5f)
                    lineTo(w * 0.2f, h * 0.5f)
                    moveTo(w * 0.45f, h * 0.25f)
                    lineTo(w * 0.2f, h * 0.5f)
                    lineTo(w * 0.45f, h * 0.75f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.1f))
            }
            CustomIconType.AGRICULTURE -> {
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.7f)
                    lineTo(w * 0.8f, h * 0.7f)
                    moveTo(w * 0.5f, h * 0.7f)
                    lineTo(w * 0.5f, h * 0.3f)
                    moveTo(w * 0.5f, h * 0.45f)
                    quadraticTo(w * 0.2f, h * 0.3f, w * 0.2f, h * 0.2f)
                    moveTo(w * 0.5f, h * 0.35f)
                    quadraticTo(w * 0.8f, h * 0.2f, w * 0.8f, h * 0.1f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.PERSON -> {
                drawCircle(color = tint, radius = w * 0.22f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.35f))
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.85f)
                    quadraticTo(w * 0.5f, h * 0.6f, w * 0.8f, h * 0.85f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.1f))
            }
            CustomIconType.BADGE -> {
                val path = Path().apply {
                    addRoundRect(androidx.compose.ui.geometry.RoundRect(
                        rect = androidx.compose.ui.geometry.Rect(w * 0.15f, h * 0.2f, w * 0.85f, h * 0.8f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.1f, w * 0.1f)
                    ))
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
                drawCircle(color = tint, radius = w * 0.1f, center = androidx.compose.ui.geometry.Offset(w * 0.38f, h * 0.5f))
            }
            CustomIconType.PHONE -> {
                val path = Path().apply {
                    moveTo(w * 0.25f, h * 0.25f)
                    lineTo(w * 0.4f, h * 0.2f)
                    lineTo(w * 0.5f, h * 0.4f)
                    lineTo(w * 0.38f, h * 0.52f)
                    quadraticTo(w * 0.48f, h * 0.72f, w * 0.68f, h * 0.82f)
                    lineTo(w * 0.8f, h * 0.7f)
                    lineTo(w * 1.0f, h * 0.8f)
                    lineTo(w * 0.95f, h * 0.95f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.LOCATION -> {
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.15f)
                    cubicTo(w * 0.25f, h * 0.15f, w * 0.25f, h * 0.55f, w * 0.5f, h * 0.85f)
                    cubicTo(w * 0.75f, h * 0.55f, w * 0.75f, h * 0.15f, w * 0.5f, h * 0.15f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
                drawCircle(color = tint, radius = w * 0.1f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.38f))
            }
            CustomIconType.GPS -> {
                drawCircle(color = tint, radius = w * 0.25f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f), style = Stroke(width = w * 0.08f))
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.1f); lineTo(w * 0.5f, h * 0.25f)
                    moveTo(w * 0.5f, h * 0.75f); lineTo(w * 0.5f, h * 0.9f)
                    moveTo(w * 0.1f, h * 0.5f); lineTo(w * 0.25f, h * 0.5f)
                    moveTo(w * 0.75f, h * 0.5f); lineTo(w * 0.9f, h * 0.5f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.WALK -> {
                drawCircle(color = tint, radius = w * 0.08f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.2f))
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.3f); lineTo(w * 0.45f, h * 0.55f)
                    lineTo(w * 0.35f, h * 0.85f)
                    moveTo(w * 0.45f, h * 0.55f); lineTo(w * 0.65f, h * 0.85f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.MAP -> {
                val path = Path().apply {
                    moveTo(w * 0.15f, h * 0.25f); lineTo(w * 0.38f, h * 0.15f)
                    lineTo(w * 0.62f, h * 0.25f); lineTo(w * 0.85f, h * 0.15f)
                    lineTo(w * 0.85f, h * 0.75f); lineTo(w * 0.62f, h * 0.85f)
                    lineTo(w * 0.38f, h * 0.75f); lineTo(w * 0.15f, h * 0.85f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.CHEVRON_RIGHT -> {
                val path = Path().apply {
                    moveTo(w * 0.35f, h * 0.25f)
                    lineTo(w * 0.65f, h * 0.5f)
                    lineTo(w * 0.35f, h * 0.75f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.1f))
            }
            CustomIconType.SAVE -> {
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.2f)
                    lineTo(w * 0.7f, h * 0.2f)
                    lineTo(w * 0.8f, h * 0.3f)
                    lineTo(w * 0.8f, h * 0.8f)
                    lineTo(w * 0.2f, h * 0.8f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.CLOUD -> {
                val path = Path().apply {
                    moveTo(w * 0.25f, h * 0.7f)
                    cubicTo(w * 0.1f, h * 0.7f, w * 0.1f, h * 0.4f, w * 0.3f, h * 0.4f)
                    cubicTo(w * 0.35f, h * 0.2f, w * 0.65f, h * 0.2f, w * 0.7f, h * 0.4f)
                    cubicTo(w * 0.9f, h * 0.4f, w * 0.9f, h * 0.7f, w * 0.75f, h * 0.7f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.VERIFIED -> {
                drawCircle(color = tint, radius = w * 0.35f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f), style = Stroke(width = w * 0.08f))
                val path = Path().apply {
                    moveTo(w * 0.33f, h * 0.5f)
                    lineTo(w * 0.45f, h * 0.62f)
                    lineTo(w * 0.67f, h * 0.38f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.08f))
            }
            CustomIconType.CHECK -> {
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.5f)
                    lineTo(w * 0.42f, h * 0.72f)
                    lineTo(w * 0.8f, h * 0.28f)
                }
                drawPath(path, color = tint, style = Stroke(width = w * 0.12f))
            }
        }
    }
}


@Composable
fun SilvanusApp() {
    var screen by remember { mutableStateOf(Screen.WELCOME) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        when (screen) {
            Screen.WELCOME -> WelcomeScreen { screen = Screen.FARMER_INFO }
            Screen.FARMER_INFO -> FarmerInfoScreen(
                onBack = { screen = Screen.WELCOME },
                onContinue = { screen = Screen.LOCATION }
            )
            Screen.LOCATION -> LocationScreen(
                onBack = { screen = Screen.FARMER_INFO },
                onContinue = { screen = Screen.MAP_OPTIONS }
            )
            Screen.MAP_OPTIONS -> MapOptionsScreen(
                onBack = { screen = Screen.LOCATION },
                onWalk = { screen = Screen.WALK_MAP },
                onDraw = { screen = Screen.DRAW_MAP }
            )
            Screen.WALK_MAP -> WalkMapScreen(
                onBack = { screen = Screen.MAP_OPTIONS },
                onFinish = { screen = Screen.NEXT }
            )
            Screen.DRAW_MAP -> DrawMapScreen(
                onBack = { screen = Screen.MAP_OPTIONS },
                onFinish = { screen = Screen.NEXT }
            )
            Screen.NEXT -> NextScreen { screen = Screen.COMPLETE }
            Screen.COMPLETE -> CompleteScreen { screen = Screen.WELCOME }
        }
    }
}



enum class Screen {
    WELCOME, FARMER_INFO, LOCATION, MAP_OPTIONS, WALK_MAP, DRAW_MAP, NEXT, COMPLETE
}



@Composable
fun AppTopBar(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            CustomIcon(type = CustomIconType.BACK, tint = TextDark)
        }

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
    }
}


@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Green, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CustomIcon(
                type = CustomIconType.AGRICULTURE,
                tint = Color.White,
                modifier = Modifier.size(55.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Silvanus",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Map your farm.\nProtect your future.",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = TextDark,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Create your farm profile and map your plot.",
            textAlign = TextAlign.Center,
            color = TextGrey,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) {
            Text(text = "Get Started", fontSize = 17.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Works offline", color = TextGrey, fontSize = 14.sp)
    }
}



@Composable
fun FarmerInfoScreen(onBack: () -> Unit, onContinue: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = "Your information", onBack = onBack)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Tell us about yourself",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "This information will be linked to your farm.",
                    color = TextGrey
                )
            }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Full name") },
                    leadingIcon = { CustomIcon(type = CustomIconType.PERSON, tint = TextGrey) },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("National ID") },
                    leadingIcon = { CustomIcon(type = CustomIconType.BADGE, tint = TextGrey) },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Phone number") },
                    leadingIcon = { CustomIcon(type = CustomIconType.PHONE, tint = TextGrey) },
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green)
                ) {
                    Text("Continue")
                }
            }
        }
    }
}


@Composable
fun LocationScreen(onBack: () -> Unit, onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTopBar(title = "Location", onBack = onBack)

        Spacer(modifier = Modifier.height(45.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(LightGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CustomIcon(
                type = CustomIconType.LOCATION,
                tint = Green,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Allow location access",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We need your location to map your farm's boundary.",
            textAlign = TextAlign.Center,
            color = TextGrey,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIcon(type = CustomIconType.GPS, tint = Green)
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text("GPS location", fontWeight = FontWeight.Bold)
                    Text("Used only to map your farm", color = TextGrey, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ) {
            CustomIcon(type = CustomIconType.LOCATION, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Allow location")
        }
    }
}


@Composable
fun MapOptionsScreen(onBack: () -> Unit, onWalk: () -> Unit, onDraw: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        AppTopBar(title = "Map your farm", onBack = onBack)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "How would you like to map it?",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose the option that works best for you.",
            color = TextGrey
        )

        Spacer(modifier = Modifier.height(30.dp))

        MapOptionCard(
            iconType = CustomIconType.WALK,
            title = "Walk around your farm",
            description = "Walk along the farm boundary while Silvanus records the shape.",
            onClick = onWalk
        )

        Spacer(modifier = Modifier.height(16.dp))

        MapOptionCard(
            iconType = CustomIconType.MAP,
            title = "Draw on the map",
            description = "Tap points on the map to outline your farm.",
            onClick = onDraw
        )
    }
}


@Composable
fun MapOptionCard(
    iconType: CustomIconType,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(LightGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                CustomIcon(
                    type = iconType,
                    tint = Green,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(description, color = TextGrey, fontSize = 14.sp)
            }

            CustomIcon(type = CustomIconType.CHEVRON_RIGHT, tint = TextGrey)
        }
    }
}


@Composable
fun WalkMapScreen(onBack: () -> Unit, onFinish: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = "Walk the plot", onBack = onBack)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFDDE7D8)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomIcon(
                    type = CustomIconType.MAP,
                    tint = Green,
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "MAP",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green
                )

                Text("Offline map will appear here", color = TextGrey)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(Green, CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text("GPS signal strong", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Walk around the boundary of your farm.", color = TextGrey)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text("Finish mapping")
            }
        }
    }
}

@Composable
fun DrawMapScreen(onBack: () -> Unit, onFinish: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = "Draw your farm", onBack = onBack)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFDDE7D8))
        ) {
            MapPin(modifier = Modifier.align(Alignment.TopStart).padding(start = 100.dp, top = 120.dp))
            MapPin(modifier = Modifier.align(Alignment.TopEnd).padding(end = 80.dp, top = 150.dp))
            MapPin(modifier = Modifier.align(Alignment.BottomEnd).padding(end = 110.dp, bottom = 130.dp))
            MapPin(modifier = Modifier.align(Alignment.BottomStart).padding(start = 90.dp, bottom = 100.dp))

            Text(
                text = "Tap the map to add points",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 25.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Text("4 points added", fontWeight = FontWeight.Bold)
            Text("Close the boundary when finished.", color = TextGrey)

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text("Save farm boundary")
            }
        }
    }
}


@Composable
fun MapPin(modifier: Modifier = Modifier) {
    CustomIcon(
        type = CustomIconType.LOCATION,
        tint = Green,
        modifier = modifier.size(38.dp)
    )
}



@Composable
fun NextScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "What happens next?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Your farm information is saved safely on your phone.",
            color = TextGrey,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        StatusItem(
            iconType = CustomIconType.SAVE,
            title = "Saved on your phone",
            description = "Your information stays available even without internet."
        )

        StatusItem(
            iconType = CustomIconType.CLOUD,
            title = "Sync automatically",
            description = "Your data will sync when an internet connection is available."
        )

        StatusItem(
            iconType = CustomIconType.VERIFIED,
            title = "Checked by TANIT",
            description = "Your farm data can be reviewed and validated."
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Continue")
        }
    }
}

// ----------------------------------------------------
// STATUS ITEM
// ----------------------------------------------------

@Composable
fun StatusItem(iconType: CustomIconType, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(LightGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CustomIcon(type = iconType, tint = Green)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, color = TextGrey, fontSize = 14.sp)
        }
    }
}

// ----------------------------------------------------
// COMPLETE
// ----------------------------------------------------

@Composable
fun CompleteScreen(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(LightGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CustomIcon(
                type = CustomIconType.CHECK,
                tint = Green,
                modifier = Modifier.size(65.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Farm saved!", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Your farm information has been saved successfully.",
            textAlign = TextAlign.Center,
            color = TextGrey,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onDone,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Done")
        }
    }
}