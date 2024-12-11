package com.example.cartapp

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.cartApp.compose.publicsansBold
import com.app.cartApp.compose.publicsansRegular
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.ui.screens.HomeScreen
import com.example.cartapp.ui.screens.ProductsScreenView
import com.example.cartapp.ui.screens.ProfileScreenView
import com.example.cartapp.ui.screens.activity.CartActivity
import kotlinx.coroutines.delay

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("home") {
                HomeScreen(navController)
            }
            composable("allProducts") {
                ProductsScreenView(navController)
            }
            composable("profile") {
                ProfileScreenView(navController)
            }
        }
    )
}

@SuppressLint("NewApi")
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Products,
        BottomBarScreen.Profile
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .padding(start=10.dp,end=10.dp,bottom=20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color("#bdd5f0".toColorInt()))
            .fillMaxWidth().border(BorderStroke(1.dp, Color("#bdd5f0".toColorInt())),
                RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}


@SuppressLint("NewApi")
@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 12.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // Only navigate if the selected tab is not already active
                if (!selected) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                    contentDescription = "",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                )
                Text(
                    modifier = Modifier,
                    text = screen.title,
                    fontSize = 12.sp,
                    fontFamily = if(selected) publicsansBold else publicsansRegular,
                    color = if (selected) Color("#000000".toColorInt()) else Color("#333333".toColorInt())
                )
            }
        }
    }
}


@Composable
fun TopBar(navController: NavController){
    val context= LocalContext.current
    Row(modifier= Modifier.fillMaxWidth().padding(top=50.dp,start=20.dp,end=20.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        val cartViewModel =hiltViewModel<CartViewModel>()
        val cartItems = cartViewModel.cartItems.collectAsState().value
        val cartSize = remember(cartItems) { cartItems.size }

        LaunchedEffect(true) {
            while (true) {
                delay(100)
                cartViewModel.fetchCartItems()
            }
        }

        Image(
            painterResource(R.drawable.profile_top),
            contentDescription = "",
            modifier = Modifier
                .width(20.dp).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    navController.navigate("profile") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
        )
        Spacer(modifier=Modifier.weight(1f))

        Text(
            text = "AYUVYA",
            modifier = Modifier.padding(top=5.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = publicsansBold,
            color = Color("#000000".toColorInt()),
        )
        Spacer(modifier=Modifier.weight(1f))
        Box( modifier = Modifier
            .size(50.dp)){
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(Color.Gray, shape = CircleShape)
            ) {
                Text(
                    text = cartSize.toString(),
                    fontSize = 8.sp,
                    color = Color.White,
                    fontFamily = publicsansBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Image(
                painterResource(R.drawable.cart_img),
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center)
                    .width(20.dp).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        val i = Intent(context, CartActivity::class.java)
                        context.startActivity(i)
                    }
            )

        }

    }
}