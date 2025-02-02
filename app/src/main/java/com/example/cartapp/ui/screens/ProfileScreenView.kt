package com.example.cartapp.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.cartApp.compose.publicsansBold
import com.app.cartApp.compose.publicsansRegular
import com.example.cartapp.data.viewmodel.CartViewModel
import com.example.cartapp.ui.screens.activity.CartActivity
import kotlinx.coroutines.delay
import com.example.cartapp.R


@Composable
fun ProfileScreenView(navController: NavController) {

    val context= LocalContext.current as Activity
    val cartViewModel =hiltViewModel<CartViewModel>()
    val cartItems = cartViewModel.cartItems.collectAsState().value
    val cartSize = remember(cartItems) { cartItems.size }

    LaunchedEffect(true) {
        while (true) {
            delay(100)
            cartViewModel.fetchCartItems()
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color("#bdd5f0".toColorInt()))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier=Modifier.padding(top=10.dp),
                text = "Account & Settings",
                fontSize = 23.sp,
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ){
            Image(
                painter = painterResource(R.drawable.sign_in),
                contentDescription = "avatar",
                modifier = Modifier.align(Alignment.Center).size(200.dp)

            )
        }

        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    BorderStroke(1.dp, Color("#333333".toColorInt())),
                    RoundedCornerShape(15)
                )
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    val i = Intent(context, CartActivity::class.java)
                    context.startActivity(i)

                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end=10.dp)
            ) {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.cart_profile),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        fontFamily = publicsansRegular,
                        text ="My cart",
                        color = Color("#333333".toColorInt()),
                        fontSize = 15.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = cartSize.toString(),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = publicsansBold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    BorderStroke(1.dp, Color("#333333".toColorInt())),
                    RoundedCornerShape(15)
                )
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()

                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end=10.dp)
            ) {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.truck),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        fontFamily = publicsansRegular,
                        text ="Track Order",
                        color = Color("#333333".toColorInt()),
                        fontSize = 15.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    BorderStroke(1.dp, Color("#333333".toColorInt())),
                    RoundedCornerShape(15)
                )
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()

                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end=10.dp)
            ) {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.mail),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        fontFamily = publicsansRegular,
                        text ="Contact Us",
                        color = Color("#333333".toColorInt()),
                        fontSize = 15.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    BorderStroke(1.dp, Color("#333333".toColorInt())),
                    RoundedCornerShape(15)
                )
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()

                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end=10.dp)
            ) {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        fontFamily = publicsansRegular,
                        text ="Rate Us",
                        color = Color("#333333".toColorInt()),
                        fontSize = 15.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .border(
                    BorderStroke(1.dp, Color("#333333".toColorInt())),
                    RoundedCornerShape(15)
                )
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show()

                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp,end=10.dp)
            ) {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.follow),
                        contentDescription = "avatar",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        fontFamily = publicsansRegular,
                        text ="Follow Us",
                        color = Color("#333333".toColorInt()),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}