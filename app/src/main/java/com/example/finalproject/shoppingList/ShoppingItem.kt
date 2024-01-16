package com.example.finalproject.shoppingList

import android.widget.RatingBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.repos.DBItem
import com.example.finalproject.ui.theme.AppTheme


@Composable
fun MyComposable() {
    LazyColumn {
        items(10) {
            ShoppingItem(rememberNavController(), DBItem("cherry", 5, 3.5F, true))
        }
    }
}


@Composable
fun ShoppingItem(navController: NavController, item: DBItem){
}

@Preview(showBackground = true)
@Composable
fun PreviewMyComposable() {
    AppTheme(useDarkTheme = true) {
        MyComposable()
    }
}