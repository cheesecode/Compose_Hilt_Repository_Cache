package com.example.test.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel  @Inject constructor() : ViewModel() {
    val countDownFlow = flow<Int> {
        val startCount = 10
        var currentVal = startCount
        emit(startCount)
        while(currentVal > 0) {
            delay(1000L)
            currentVal--
            emit(currentVal)
        }

    }

    init {
        countDown()
    }

    private fun countDown() {
        viewModelScope.launch {
            countDownFlow.collect {
                println("Counting down $it")
            }
        }
    }


    var presses = mutableIntStateOf(0)
        private set

    fun incrementPresses() {
        presses.value++
    }
}