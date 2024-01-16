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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
import com.example.finalproject.repos.DBItem
import com.example.finalproject.repos.DataRepo
import com.example.finalproject.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(navController: NavController, id: Int, itemList: List<DBItem>){

    val isModification = id != -1
    val context = LocalContext.current

    val shopItems = arrayOf("banana", "cherry", "plum", "mandarin", "mango", "pear", "apple", "pineapple")
    var expanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf(shopItems[0]) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var selectedImage by remember { mutableIntStateOf(R.drawable.banana) }

    val dbItem = remember { mutableStateOf(DBItem("banana", -1, -1f, false)) }


    if (isModification) {
        dbItem.value = itemList.find { it.id == id }!!
        selectedText = dbItem.value.name
        sliderPosition = dbItem.value.rating
        selectedImage = dbItem.value.image
    }

    var checkedState = remember { mutableStateOf(dbItem.value.inBasket) }


    var number by remember { mutableStateOf(if(dbItem.value.number != -1) dbItem.value.number.toString() else "") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
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
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                when (selectedText) {
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
        }

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Number of Fruits") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
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
            Text(text = sliderPosition.toString(),
                textAlign = TextAlign.Center)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "In basket:",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
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



        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            contentScale = ContentScale.Crop
        )

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

                    if (isModification) {
                        DataRepo.getInstance(context).modifyItem(dbItem.value)
                    } else {
                        DataRepo.getInstance(context).addItem(dbItem.value)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                if(isModification){
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    Text("Save")
                } else {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text("Add")
                }
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