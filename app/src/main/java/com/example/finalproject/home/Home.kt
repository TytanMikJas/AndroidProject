package com.example.finalproject.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.ui.theme.AppTheme

val LightBlue = Color(0xFF0066FF)
val Purple = Color(0xFF800080)

@Composable
fun Home(){
    val context = LocalContext.current
    val data = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    val shopName = data.getString("shopName", "Local Shop")
    val image = data.getInt("image", R.drawable.store)
    val shopDesc = data.getString("shopDesc", "Your favorite Shop app!")


    Column(
        modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = shopName!!,
            style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(Purple, LightBlue, Cyan)
                )
            ),
            fontSize = 38.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 125.dp)
        )

        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .background(MaterialTheme.colorScheme.background)
                .size(250.dp),
            contentScale = ContentScale.Crop
        )

        Text(text = shopDesc!!,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Cyan, LightBlue, Purple)
                )
            ),
            fontSize = 28.sp,
            fontWeight = FontWeight.Light)

        Button(
            onClick = {
                val query = "sklep spożywczy"
                val uri = Uri.parse("geo:0,0?q=$query")
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)

                if (mapIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapIntent)
                } else {
                    Log.d("CUSTOM", "NE MA KURWA APKI")
                }
            },
            modifier = Modifier
                .width(270.dp)
                .padding(16.dp, top = 75.dp)
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Seach for nearby shops")
        }

        Button(
            onClick = {
                val websiteUrl = "https://www.przepisy.pl"
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                context.startActivity(webIntent)
            },
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp, top = 5.dp)
        ) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Get inspired")
        }

        
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AppTheme {
        Home()
    }
}