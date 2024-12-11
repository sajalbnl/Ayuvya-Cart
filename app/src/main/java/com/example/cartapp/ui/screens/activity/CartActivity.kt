package com.example.cartapp.ui.screens.activity


import android.app.Activity
import android.content.Intent
import com.example.cartapp.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.app.cartApp.compose.publicsansBold
import com.app.cartApp.compose.publicsansRegular
import com.example.cartapp.data.model.CartItem
import com.example.cartapp.data.model.ProductData
import com.example.cartapp.data.model.Rating
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.ui.theme.CartAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CartActivity : ComponentActivity() {
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
                    CartScreenView(navController)

                }
            }
        }
    }

    @Composable
    fun CartScreenView(navController: NavController){

        val cartViewModel =hiltViewModel<CartViewModel>()
        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
        // Observe the cart items
        val cartItems = cartViewModel.cartItems.collectAsState()
        val context= LocalContext.current as Activity
        var totalPrice = 0.0
        cartItems.value.forEach { item ->
            totalPrice += item.product.price * item.quantity
        }
        val decimalFormat = DecimalFormat("#.##")
        val discountedPrice = decimalFormat.format(totalPrice * 0.8)
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
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 25.dp, bottom = 15.dp)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
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
                    Text(
                        text = "Cart",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        fontFamily = publicsansBold,
                        color = Color("#333333".toColorInt())
                    )
                }
            }

            if(cartItems.value.size==0){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Text(
                            text ="Your Cart Is Empty",
                            fontSize = 20.sp,
                            color = Color("#242533".toColorInt()),
                            fontFamily = publicsansBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }else{
                LazyColumn(modifier = Modifier.padding(top=10.dp).weight(1f)) {
                    items(cartItems.value.size) { index ->
                        ProductItem(product = cartItems.value[index],
                            onDeleteCartItem = { cartViewModel.deleteCartItem(it) },
                            onIncreaseQuantity = {cartViewModel.increaseQuantity(it)},
                            onDecreaseQuantity = {cartViewModel.decreaseQuantity(it)})
                    }
                }
                Row(modifier= Modifier.padding(start = 30.dp,end=30.dp,bottom=10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Column {
                        Text(text = "₹$discountedPrice", fontFamily =publicsansBold, fontSize = 16.sp)
                        Text(text = "Grand total", fontFamily = publicsansRegular, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()} ,
                        modifier = Modifier.width(itemSize),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Check Out")
                    }

                }

            }

        }

    }

    @Composable
    fun ProductItem(product: CartItem,onDeleteCartItem: (Int) -> Unit,
                    onIncreaseQuantity: (CartItem) -> Unit,
                    onDecreaseQuantity: (CartItem) -> Unit) {


        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.product.image)
                        .apply { crossfade(true) }
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = product.product.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val price=product.product.price * product.quantity
                        Text(
                            text = "₹${price}",
                            fontFamily = publicsansBold,
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        val decimalFormat = DecimalFormat("#.##")
                        val discountedPrice = decimalFormat.format(price * 0.8)
                        Text(
                            text = "₹$discountedPrice",
                            fontFamily = publicsansBold,
                            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "20% off",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color("#37877f".toColorInt())),
                        fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = { onDecreaseQuantity(product) }) {
                            Icon(painterResource(R.drawable.minus),modifier= Modifier.size(10.dp), contentDescription = "Decrease Quantity")
                        }
                        Text(text = product.quantity.toString(), modifier = Modifier.padding(8.dp))
                        IconButton(onClick = {onIncreaseQuantity(product)}) {
                            Icon(painterResource(R.drawable.plus),modifier= Modifier.size(15.dp), contentDescription = "Increase Quantity")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    IconButton(onClick = { onDeleteCartItem(product.product.id) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }
}
