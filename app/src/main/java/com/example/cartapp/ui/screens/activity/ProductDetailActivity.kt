package com.example.cartapp.ui.screens.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.app.cartApp.compose.publicsansBold
import com.app.cartApp.compose.publicsansRegular
import com.example.cartapp.R
import com.example.cartapp.data.model.ProductData
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.ui.theme.CartAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            CartAppTheme {
                // A surface container using the 'background' color from the theme
                window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()

                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize(), color = Color("#ffffff".toColorInt())) {
                    ProductDetailScreen(navController)

                }
            }
        }
    }

    @Composable
    fun ProductDetailScreen(navController: NavController){
        val context= LocalContext.current as Activity
        val product=context.intent.getSerializableExtra(
            "Product"
        ) as ProductData

        val cartViewModel =hiltViewModel<CartViewModel>()
        val cartItems = cartViewModel.cartItems
        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
        val existingItem = cartItems.find { it.product.id == product.id }
        val quantity = existingItem?.quantity ?: 0

        Column(
            modifier = Modifier

                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color("#bdd5f0".toColorInt()),
                            Color("#ffffff".toColorInt()),
                        )
                    )
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 25.dp, bottom = 15.dp,end=20.dp)
                ) {

                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        modifier = Modifier
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                context.finish()
                            }
                            .size(25.dp),
                        contentDescription = null,
                        tint = Color("#333333".toColorInt())
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text ="Product Detail",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        fontFamily = publicsansBold,
                        color = Color("#333333".toColorInt())
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painterResource(R.drawable.cart_img),
                        contentDescription = "",
                        modifier = Modifier
                            .width(25.dp).clickable(){
                                val i = Intent(context, CartActivity::class.java)
                                context.startActivity(i)
                            }
                    )
                }
            }
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(5.dp).weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(250.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color("#bdd5f0".toColorInt()))
                        .padding(1.dp)
                        .background(Color("#bdd5f0".toColorInt())),
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
                            .width(itemSize)
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Product Name
                Text(
                    text = product.title,
                    fontFamily = publicsansBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start=15.dp,end=15.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = product.description,
                    fontFamily = publicsansRegular,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start=15.dp,end=15.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))
                // Pricing and Discount
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start=15.dp,end=15.dp)
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
                    style = MaterialTheme.typography.bodySmall.copy(color = Color("#37877f".toColorInt())),
                    modifier = Modifier.padding(start=15.dp,end=15.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))


                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start=15.dp,end=15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text =product.rating.rate.toFloat().toString(),
                        //   style = MaterialTheme.typography.body2
                    )
                }


            }


            // Add to Cart Button
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(start=15.dp,end=15.dp, bottom = 10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "ADD TO CART")
            }
        }

    }
}