package com.example.test.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.test.model.User
import com.example.test.viewmodel.UserViewModel

class AccountScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val userViewModel = getViewModel<UserViewModel>()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
        ) {
                AccountContent(userViewModel)

            }
        }
    }


    @Composable
    fun AccountContent(userViewModel: UserViewModel ) {
        val navigator = LocalNavigator.current

        userViewModel.fetchUser(1)
        val userFlow = userViewModel.userFlow.collectAsState(initial = null)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                userFlow.value?.let { user ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentHeight()
                    ) {
                        UserCard(user) {

                            //save the user here
                            println("***************** User saved")
                        }
                    }
                }


            // Placeholder for loading state
            if (userFlow.value == null) {

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
@Composable
fun UserCard(user: User, onUserUpdated: (User) -> Unit) {
    var editedUser by remember { mutableStateOf(user.copy()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = editedUser.firstName,
                onValueChange = { editedUser = editedUser.copy(firstName = it) },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.lastName,
                onValueChange = { editedUser = editedUser.copy(lastName = it) },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.age.toString(),
                onValueChange = { editedUser = editedUser.copy(age = it.toIntOrNull() ?: 0) },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.gender,
                onValueChange = { editedUser = editedUser.copy(gender = it) },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.email,
                onValueChange = { editedUser = editedUser.copy(email = it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.phone,
                onValueChange = { editedUser = editedUser.copy(phone = it) },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editedUser.username,
                onValueChange = { editedUser = editedUser.copy(username = it) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Add more fields as needed
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.image)
                    .crossfade(true)
                    .build(),

                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onUserUpdated(editedUser) }) {
                Text("Save")
            }
        }
    }
}



