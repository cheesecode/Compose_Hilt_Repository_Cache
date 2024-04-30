package com.example.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.model.User
import com.example.test.repository.Repository
import com.example.test.service.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _userFlow = MutableStateFlow<User?>(null)
    val userFlow: Flow<User?> = _userFlow

    private val _usersFlow = MutableStateFlow<List<User>?>(null)
    val usersFlow: StateFlow<List<User>?> = _usersFlow

    init {
        // fetchUser(1)
        // fetchUsers(30)
    }

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            try {
                val user = repository.fetchUser(userId)
                _userFlow.value = user
            } catch (e: Exception) {
                // Handle error
                _userFlow.value = null
                e.printStackTrace()
            }
        }
    }

    fun fetchUsers(amount: Int) {
        viewModelScope.launch {
            try {
                val allUsers = repository.getAllUsers()
                val limitedUsers = allUsers.take(amount)
                _usersFlow.value = limitedUsers
            } catch (e: Exception) {
                // Handle error
                _usersFlow.value = null
                e.printStackTrace()
            }
        }
    }
}
