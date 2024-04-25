package com.example.test



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.test.ui.dashboard.ui.theme.BlaTheme
import com.example.test.viewmodel.MainActivityViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigator = LocalNavigator.current
         //   val viewModel: MainActivityViewModel = viewModel()
            val viewModel = viewModel<MainActivityViewModel>()

            BlaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background,
                ) {
                    ScaffoldExample(viewModel)
                }
            }
        }
    }
}

@Composable
fun GreetingColumn(name: String, modifier: Modifier = Modifier, viewModel: MainActivityViewModel ) {

    val navigator = LocalNavigator.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        items(10) {


                Column ( modifier = modifier

                 ) {

                    Box(modifier = modifier
                        .width(400.dp)
                        .height(400.dp)
                        .padding(PaddingValues(bottom = 16.dp))) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://www.geeky-gadgets.com/wp-content/uploads/2023/10/How-to-use-DallE-3-in-ChatGPT.webp")
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.chigo),
                            contentDescription = "",
                           contentScale = ContentScale.Crop,
                            modifier = modifier
                                .fillMaxWidth()

                                .clickable(onClick = {
                                    navigator?.push(DetailScreen())
                                }
                                )

                        )
                        Surface(
                            onClick = { viewModel.incrementPresses() },
                            Modifier
                                .align(Alignment.BottomCenter)
                                .padding(PaddingValues(bottom = 16.dp))
                                .width(120.dp)
                                .shadow(
                                    elevation = 10.dp
                                )
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.White)

                        ) {
                            Text(

                                text = "Click me",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = modifier
                                    .background(Color.White)

                            )
                        }


                    }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScaffoldExample(viewModel : MainActivityViewModel = viewModel()) {

    val counter = viewModel.countDownFlow.collectAsState(initial = 10)

    TabNavigator(HomeTab) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Counter: ${counter.value} | Clicked: ${viewModel.presses.value}")
                }
            )


        },
        bottomBar = {
            NavigationBar() {
            TabNavigationItem(tab = HomeTab)
            TabNavigationItem(tab = DetailTab)
            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {  viewModel.incrementPresses() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding()),

        ) {
            CurrentTab()
           // GreetingColumn("Dev", Modifier, viewModel)
        }
    }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab : Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current ==tab ,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let {
            Icon(
                painter = it,
                contentDescription  = tab.options.title
            )
        }
        
        },
        label = { Text(text =tab.options.title )}
        )

}