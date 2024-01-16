package com.example.finalproject.shoppingList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.repos.DBItem
import com.example.finalproject.repos.DataRepo
import com.example.finalproject.ui.theme.AppTheme
import java.security.AllPermission


@Composable
fun MyComposable() {
    LazyColumn {
        items(10) {
            ShoppingItem(rememberNavController(),  DBItem("cherry", 5, 3.5F, true))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingItem(navController: NavController, item: DBItem){
    val context = LocalContext.current
    val popup = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
            .background(Color(0xFF005AC5))
            .combinedClickable(
                onClick = { navController.navigate("ItemDetails/" + item.id.toString()) },
                onLongClick = { popup.value = true }
            )
        )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = null,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(4f)
                    .padding(end = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp),
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Text(
                    text = "Number: " + item.number.toString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            
            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = Color.Gray ),
                modifier = Modifier.padding(end = 10.dp),
                checked = item.inBasket,
                onCheckedChange = { item.inBasket = it
                })
        }
        
        

        if(popup.value){
            Popup(
                alignment = Alignment.TopStart,
                properties = PopupProperties()
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ){
                    Column(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "Are you sure about deleting " + item.name + " ?",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = "You currently need " + item.number + " of them",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            text = if (item.inBasket) "already in basket" else "not in basket",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        Button(
                            onClick = { popup.value = false },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "No")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "no")
                        }

                        Button(
                            onClick = {
                                DataRepo.getInstance(context).deleteItem(item)
                                popup.value = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Yes")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "yes")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingItem() {
    AppTheme(useDarkTheme = false) {
        MyComposable()
    }
}