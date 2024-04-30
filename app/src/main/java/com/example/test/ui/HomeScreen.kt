package com.example.test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.test.viewmodel.ProductViewModel

class HomeScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val productViewModel = getViewModel<ProductViewModel>()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           // ProductsScreen(amount = 30, productViewModel)
            HomeContent(productViewModel)
        }
    }
}

@Composable
fun HomeContent(productViewModel: ProductViewModel = viewModel()) {
    val navigator = LocalNavigator.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProductsScreen(amount = 30, productViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContent() {
    val navigator = LocalNavigator.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       Text(text = "preview products", modifier = Modifier)
    }
}