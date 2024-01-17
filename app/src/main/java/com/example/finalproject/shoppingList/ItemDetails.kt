package com.example.finalproject.shoppingList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.home.LightBlue
import com.example.finalproject.home.Purple
import com.example.finalproject.repos.DBItem
import com.example.finalproject.ui.theme.AppTheme

@Composable
fun ItemDetails(navController: NavController, id: Int, itemList: List<DBItem>){
    val dbItem = itemList.find { it.id == id }!!
    val colors = listOf(Purple, LightBlue)

    val name = dbItem.name
    val number = dbItem.number.toString()
    val rating = dbItem.rating
    val inBasket = dbItem.inBasket
    val image = dbItem.image


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop
        )


        Text(
            text = name,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = colors
                )
            ),
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 25.dp)
        )

        Text(
            text = "You need $number of them",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = colors
                )
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
        )

        when(rating) {
            0.0F -> Urgency("Not important", Color.LightGray)
            0.25F -> Urgency("Might needed", Color.Gray)
            0.5F -> Urgency("Consider buying", Color.DarkGray)
            0.75F -> Urgency("You should buy it", LightBlue)
            1F -> Urgency("Very necessary", Purple)
            else -> Urgency("None", Color.Gray)
        }



        when (inBasket) {
            true -> BasketShowcase("Item already in basket", R.drawable.full_basket)
            false ->BasketShowcase("Item not in basket", R.drawable.empty_basket)
        }

        Button(
            onClick = { navController.navigate("ModifyItem/" + dbItem.id.toString()) },
            modifier = Modifier
                .width(100.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Edit",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun Urgency(message: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "Necessity:",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = message,
                color = color,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun BasketShowcase(message: String, basketImage: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "In Basket:",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Purple, LightBlue)
                        )
                    ),
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(id = basketImage),
                    contentDescription = null,
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewItemDetails(){
    AppTheme {
        ItemDetails(navController = rememberNavController() , id = 8, itemList = listOf(DBItem("cherry", 12, 0.75F, true)) )
    }
}