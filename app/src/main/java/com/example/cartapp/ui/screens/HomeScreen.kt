package com.example.cartapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cartapp.R
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.app.cartApp.compose.publicsansBold
import com.app.cartApp.compose.publicsansSemiBold
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.data.viewmodel.ProductViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    val productViewModel = hiltViewModel<ProductViewModel>()
    val productsList=productViewModel.products.observeAsState(emptyList())
    val cartViewModel =hiltViewModel<CartViewModel>()

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
        colors = listOf(
            Color("#bdd5f0".toColorInt()),
            Color("#ffffff".toColorInt()),
        )
    )),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.padding(10.dp)){
                    Image(
                        painterResource(R.drawable.star), // Replace with the desired icon
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Unlock the advanced features by logging in.",
                        fontFamily = publicsansSemiBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }


                // Button
                Button(
                    onClick = { Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "LOGIN NOW", fontFamily = publicsansBold)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 5.dp)) {
            Text(
                text = "Popular Products",
                fontSize = 15.sp,
                color = Color("#333333".toColorInt()),
                fontFamily = publicsansBold,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "View All",
                fontSize = 15.sp,
                color = Color("#5fbade".toColorInt()),
                fontFamily = publicsansBold,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    navController.navigate("allProducts") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
        if(productsList.value.isEmpty()){
            ExcitingDealsShimmerLoaderView()
        }else {
            Row(modifier= Modifier.padding(10.dp)) {
                ProductCard(
                    product = productsList.value[1],
                    onAddToCartClick = { cartViewModel.addOrUpdateCartItem(it) },
                    onIncreaseQuantity = { cartViewModel.increaseQuantity(it) },
                    onDecreaseQuantity = { cartViewModel.decreaseQuantity(it) }
                )
                ProductCard(
                    product = productsList.value[2],
                    onAddToCartClick = { cartViewModel.addOrUpdateCartItem(it) },
                    onIncreaseQuantity = { cartViewModel.increaseQuantity(it) },
                    onDecreaseQuantity = { cartViewModel.decreaseQuantity(it) }
                )
            }
        }





    }
}

