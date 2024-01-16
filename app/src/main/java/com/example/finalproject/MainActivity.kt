package com.example.finalproject

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.finalproject.home.Home
import com.example.finalproject.profile.Profile
import com.example.finalproject.repos.DBItem
import com.example.finalproject.repos.DataRepo
import com.example.finalproject.shoppingList.AddItem
import com.example.finalproject.shoppingList.ItemDetails
import com.example.finalproject.shoppingList.ShoppingList
import com.example.finalproject.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(useDarkTheme = false) {
                val navController = rememberNavController()
                App(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (modifier = Modifier.width(250.dp),) {

                DrawerHeader()
                Divider()

                DrawerItem(
                    label = "Home",
                    route = "Home",
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    icon = Icons.Default.Home
                )

                DrawerItem(
                    label = "List",
                    route = "List",
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    icon = Icons.Default.List
                )

                DrawerItem(
                    label = "Profile",
                    route = "Profile",
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    icon = Icons.Default.Person
                )

            }
        },
    ) {
        Scaffold(
            topBar = { CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp) // Adjust spacing as needed
                    ) {
                        Text(text = "ShopApp")
                        Icon(
                            painter = painterResource(R.drawable.topbaricon),
                            contentDescription = "Shop Icon",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD8E2FF),
                )
            )
            }
        ) { paddingValues ->
            AppNavigation(Modifier.padding(top = paddingValues.calculateTopPadding()), navController)
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val repositoryState = DataRepo.getInstance(context).getData().collectAsState(initial = emptyList<DBItem>())
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") {
                Home()
            }
            composable("Profile") {
                Profile()
            }
            navigation(
                startDestination = "ShoppingList",
                route = "List"
            ){
                composable("ShoppingList") {
                    ShoppingList(navController, itemList = repositoryState.value)
                }
                composable("AddItem") {
                    AddItem(navController, -1, repositoryState.value)
                }
                composable("ItemDetails/{itemId}", arguments = listOf(navArgument("itemId") {type = NavType.IntType})) { backStackEntry ->
                    ItemDetails(navController = navController, id = backStackEntry.arguments!!.getInt("itemId"), itemList = repositoryState.value)
                }
                composable("ModifyItem/{itemId}", arguments = listOf(navArgument("itemId") {type = NavType.IntType})) { backStackEntry ->
                    AddItem(navController = navController, id = backStackEntry.arguments!!.getInt("itemId"), itemList = repositoryState.value)
                }
            }

        }
    }
}

@Composable
fun DrawerHeader(){
    Column(
        modifier = Modifier.fillMaxWidth()
                            .background(color = Color(0xFFD8E2FF)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.drawer_header_logo),
            contentDescription = "Jetpack compose",
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = "Navigation",
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun DrawerItem(label: String, route: String, navController: NavController, closeDrawer: () -> Unit, icon: ImageVector) {
    NavigationDrawerItem(
        modifier = Modifier.padding(10.dp),
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null)
                Text(text = label)
            }
        },
        selected = false,
        onClick = { navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
            closeDrawer()
        },
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme(useDarkTheme = false) {
        val navController = rememberNavController()
        App(navController)
    }
}