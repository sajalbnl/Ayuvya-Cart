package com.example.cartapp.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.cartapp.R
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.cartApp.compose.publicsansBold
import com.example.cartapp.data.viewmodel.ProductViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cartapp.data.model.CartItem
import com.example.cartapp.data.model.ProductData
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.ui.screens.activity.ProductDetailActivity
import kotlinx.coroutines.delay
import java.text.DecimalFormat


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductsScreenView(navController: NavController) {

    val productViewModel = hiltViewModel<ProductViewModel>()
    val productsList=productViewModel.products.observeAsState(emptyList())
    val cartViewModel =hiltViewModel<CartViewModel>()

    LaunchedEffect(Unit) {
        productViewModel.fetchProducts()
    }
    LazyColumn(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
        colors = listOf(
            Color("#bdd5f0".toColorInt()),
            Color("#ffffff".toColorInt()),
        )
    ))) {
        item{
            FlowRow(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                if(productsList.value.isEmpty()){
                    ExcitingDealsShimmerLoaderView()
                }else{
                    productsList.value.forEach { product ->

                        ProductCard(
                            product=product,
                            onAddToCartClick = { cartViewModel.addOrUpdateCartItem(it) },
                            onIncreaseQuantity = {cartViewModel.increaseQuantity(it)},
                            onDecreaseQuantity = {cartViewModel.decreaseQuantity(it)}
                        )

                    }
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductCard(
    product: ProductData,
    onAddToCartClick: (ProductData) -> Unit,
    onIncreaseQuantity: (CartItem) -> Unit,
    onDecreaseQuantity: (CartItem) -> Unit
) {
    val cartViewModel =hiltViewModel<CartViewModel>()
    val context=LocalContext.current
    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
    // Observe the cart items
    val cartItems = cartViewModel.cartItems.collectAsState()
    val existingItem = cartItems.value.find { it.product.id == product.id }
    val quantity = existingItem?.quantity ?: 0

    LaunchedEffect(true) {
        while (true) {
            delay(100)
            cartViewModel.fetchCartItems()
        }
    }

    Card(
        modifier = Modifier.width(itemSize)
            .padding(8.dp).clickable(){
                val i = Intent(context, ProductDetailActivity::class.java)
                i.putExtra("Product", product)
                context.startActivity(i)
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.image)
                        .apply { crossfade(true) }
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                // Product Name
                Text(
                    text = product.title,
                    fontFamily = publicsansBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Pricing and Discount
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${product.price}",
                        fontFamily = publicsansBold,
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    val decimalFormat = DecimalFormat("#.##")
                    val discountedPrice = decimalFormat.format(product.price * 0.8)
                    Text(
                        text = "₹$discountedPrice",
                        fontFamily = publicsansBold,
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }

                Text(
                    text = "20% off",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color("#37877f".toColorInt()))
                )
                Spacer(modifier = Modifier.height(4.dp))


                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rating.rate.toFloat().toString(),
                        //   style = MaterialTheme.typography.body2
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                if (existingItem != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { onDecreaseQuantity(existingItem) }) {
                            Icon(
                                painter = painterResource(R.drawable.minus),
                                modifier = Modifier.size(10.dp),
                                contentDescription = "Decrease Quantity"
                            )
                        }
                        Text(text = quantity.toString(), modifier = Modifier.padding(8.dp))
                        IconButton(onClick = { onIncreaseQuantity(existingItem) }) {
                            Icon(
                                painterResource(R.drawable.plus),
                                modifier = Modifier.size(15.dp),
                                contentDescription = "Increase Quantity"
                            )
                        }
                    }
                } else {
                    // Add to Cart Button
                    Button(
                        onClick = { onAddToCartClick(product) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "ADD TO CART")
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable

fun ExcitingDealsShimmerLoaderView() {
    val items = (1..15).map { "Item $it" }
    Box(modifier = Modifier.fillMaxSize().padding(top = 20.dp)) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),

                ) {
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(24.dp))
                            .background(color = Transparent)
                            .width(180.dp)
                            .padding(start = 15.dp)
                            .height(200.dp)

                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .background(color = Color.LightGray)
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .shimmerLoadingAnimationApi()
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(color = Color.LightGray)
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .shimmerLoadingAnimationApi()

                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .background(color = Color.LightGray)
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .shimmerLoadingAnimationApi()

                            )
                        }
                    }
                }
            }
        }
    }
}
fun Modifier.shimmerLoadingAnimationApi(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.1f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}

