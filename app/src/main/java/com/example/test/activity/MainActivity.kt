package com.example.test.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.test.ui.AccountTab
import com.example.test.ui.HomeTab

import com.example.test.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigator = LocalNavigator.current

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                     ScaffoldMain()
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ScaffoldMain() {

    val viewModel = viewModel<MainActivityViewModel>()
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
            TabNavigationItem(tab = AccountTab)
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
                .padding(bottom = innerPadding.calculateBottomPadding(), top = innerPadding.calculateTopPadding()),

        ) {
            CurrentTab()

        }
    }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab : Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title
                )
            }

        },
        label = { Text(text = tab.options.title) }
    )

}