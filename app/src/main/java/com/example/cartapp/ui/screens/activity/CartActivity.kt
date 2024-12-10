package com.example.cartapp.ui.screens.activity


import android.app.Activity
import com.example.cartapp.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.rechargezap.compose.publicsansBold
import com.app.rechargezap.compose.publicsansSemiBold
import com.example.cartapp.ui.theme.CartAppTheme

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

        val context= LocalContext.current as Activity

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
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
        }
    }
}