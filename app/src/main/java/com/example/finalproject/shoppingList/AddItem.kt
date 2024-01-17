package com.example.finalproject.shoppingList

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.finalproject.repos.DataRepo
import com.example.finalproject.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(navController: NavController, id: Int, itemList: List<DBItem>){

    val colors = listOf(Purple, LightBlue)
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val shopItems = arrayOf("Banana", "Cherry", "Plum", "Mandarin", "Mango", "Pear", "Apple", "Pineapple")
    var expanded by remember { mutableStateOf(false) }

    val dbItem = remember { mutableStateOf(DBItem("", -1, -1f, false)) }


    if (id != -1) {
        dbItem.value = itemList.find { it.id == id }!!
    }

    var selectedText by remember { mutableStateOf( if(dbItem.value.name != "") dbItem.value.name else shopItems[0] ) }
    var sliderPosition by remember { mutableFloatStateOf( if(dbItem.value.rating != -1F) dbItem.value.rating else 0F  ) }
    var selectedImage by remember { mutableIntStateOf( if(dbItem.value.image != R.drawable.banana) dbItem.value.image else R.drawable.banana ) }
    var checkedState = remember { mutableStateOf(dbItem.value.inBasket) }


    var number by remember { mutableStateOf(if(dbItem.value.number != -1) dbItem.value.number.toString() else "") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Choose an item",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = colors
                )
            ),
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 20.dp)
        )


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                shopItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item)},
                        onClick = {
                            selectedText = item
                            expanded = false
                            when (selectedText.lowercase()) {
                                "banana" -> selectedImage = R.drawable.banana
                                "cherry" -> selectedImage = R.drawable.cherry
                                "plum" -> selectedImage = R.drawable.plum
                                "mandarin" -> selectedImage = R.drawable.mandarin
                                "mango" -> selectedImage = R.drawable.mango
                                "pear" -> selectedImage = R.drawable.pear
                                "apple" -> selectedImage = R.drawable.apple
                                "pineapple" -> selectedImage = R.drawable.pineapple
                            }
                        }
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = {Text(
                        text = "Number of Fruits",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = colors)),
                        fontSize = 16.sp)
                    },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Text(
            text = "How necessary?",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = colors
                )
            ),
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 25.dp)
        )
        Column {
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                steps = 3,
                valueRange = 0f..1f
            )
            Text(text = when(sliderPosition){
                            0.0F -> "Don't really need"
                            0.25F -> "Might buy"
                            0.5F -> "Can buy"
                            0.75F -> "Should buy"
                            1F -> "Very necessary"
                            else -> "???"
                        },
                textAlign = TextAlign.Center)
        }

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "In basket:",
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = colors
                    )
                ),
                fontSize = 16.sp
            )

            Checkbox(
                colors = CheckboxDefaults.colors(checkedColor = Color.Gray),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically),
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }

        Spacer(modifier = Modifier.height(25.dp))


        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(25.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Cancel")
            }

            Button(
                onClick = {
                    if(selectedText != "") { dbItem.value.name = selectedText }
                    if(number != "") { dbItem.value.number = number.toInt() }
                    dbItem.value.rating = sliderPosition
                    when (dbItem.value.name.lowercase()) {
                        "banana" -> dbItem.value.image = R.drawable.banana
                        "cherry" -> dbItem.value.image = R.drawable.cherry
                        "plum" -> dbItem.value.image = R.drawable.plum
                        "mandarin" -> dbItem.value.image = R.drawable.mandarin
                        "mango" -> dbItem.value.image = R.drawable.mango
                        "pear" -> dbItem.value.image = R.drawable.pear
                        "apple" -> dbItem.value.image = R.drawable.apple
                        "pineapple" -> dbItem.value.image = R.drawable.pineapple
                    }

                    dbItem.value.inBasket = checkedState.value

                    if (id != -1) {
                        DataRepo.getInstance(context).modifyItem(dbItem.value)
                    } else {
                        DataRepo.getInstance(context).addItem(dbItem.value)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text("Add")
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyItem() {
    AppTheme(useDarkTheme = false) {
        val items = listOf(DBItem("banana", 5, 3.5F, true))
        AddItem(rememberNavController(), -1, itemList = items)
    }
}