package com.example.test.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.test.viewmodel.ProductViewModel


@Composable
fun ProductsScreen(amount: Int, productViewModel : ProductViewModel) {


    //val productState by productViewModel.productFlow.collectAsState(null)
    productViewModel.fetchProducts(amount)

    val productsState = productViewModel.productsFlow.collectAsState()

    LazyColumn(
       // modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        productsState.value?.let { products ->
            itemsIndexed(products) { index, product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Product ID: ${product.id}")
                        Text(text = "Title: ${product.title}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Description: ${product.description}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Price: $${product.price}")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Discount: ${product.discountPercentage}% off")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Rating: ${product.rating}")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Stock: ${product.stock}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = Color.LightGray)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(product.thumbnail)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            product.images.forEach { imageUrl ->
                                item {
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.LightGray)
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(imageUrl)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Placeholder for loading state
        if (productsState.value == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentHeight()
                ) {
                    Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}
