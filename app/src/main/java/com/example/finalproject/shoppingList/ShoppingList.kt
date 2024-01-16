package com.example.finalproject.shoppingList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.home.LightBlue
import com.example.finalproject.home.Purple
import com.example.finalproject.repos.DBItem
import com.example.finalproject.ui.theme.AppTheme

@Composable
fun ShoppingList(navController: NavController, itemList: List<DBItem>){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Shopping List",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Purple, LightBlue, Color.Cyan)
                    )
                ),
                fontSize = 38.sp,
                fontWeight = FontWeight.SemiBold,
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(itemList) {
                    ShoppingItem(navController, it)
                }
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("AddItem")
            },
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun PreviewShoppingList(){
    AppTheme {
        val items = listOf(DBItem("cherry", 5, 3.5F, true))
        ShoppingList(rememberNavController(), itemList = items)
    }
}