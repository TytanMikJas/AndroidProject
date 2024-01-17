package com.example.finalproject.profile

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusTargetModifierNode
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.home.LightBlue
import com.example.finalproject.home.Purple
import com.example.finalproject.ui.theme.AppTheme
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Profile(){

    val context = LocalContext.current
    val data = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    val editor = data.edit()

    var shopName by remember { mutableStateOf(data.getString("shopName", "Local Shop")!!) }

    val image = data.getInt("image", R.drawable.store)
    var shopDesc by remember { mutableStateOf(data.getString("shopDesc", "Your favorite Shop app!")!!) }

    val shopImages = listOf(R.drawable.store, R.drawable.store2, R.drawable.store3)
    val shopIndex = shopImages.indexOf(image)

    val pagerState = rememberPagerState(
        pageCount = { shopImages.size },
        initialPage = shopIndex)

    val gradientColors = listOf(Purple, LightBlue, Color.Cyan)

    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = "Configure your profile",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            ),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 60.dp)
        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            value = shopName,
            onValueChange = { shopName = it
                                editor.putString("shopName", shopName)
                                editor.apply()},
            label = { Text(
                            text = "Shop name",
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = gradientColors.reversed()
                                )
                            ),
                     )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )


        HorizontalPager(state = pagerState,
            contentPadding = PaddingValues(start = 25.dp),
            pageSize = PageSize.Fixed(400.dp)
        ) { page ->

            editor.putInt("image", shopImages[pagerState.currentPage])
            editor.apply()

            Image(
                modifier = Modifier.size(360.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = shopImages[page]),
                contentDescription = "")
        }


        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp),
            value = shopDesc,
            onValueChange = { shopDesc = it
                                editor.putString("shopDesc", shopDesc)
                                editor.apply()},
            label = { Text(
                        text = "Shop description",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = gradientColors.reversed()
                            )
                        ),
                    )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    AppTheme(useDarkTheme = false) {
        Profile()
    }
}