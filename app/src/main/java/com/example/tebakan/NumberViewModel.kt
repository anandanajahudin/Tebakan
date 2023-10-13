package com.example.tebakan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NumberViewModel : ViewModel() {

    private val _numberToGuess = MutableLiveData<Int>()
    val numberToGuess: LiveData<Int> = _numberToGuess

    private val _attemptsRemaining = MutableLiveData(3)
    val attemptsRemaining: LiveData<Int> = _attemptsRemaining

    init {
        generateRandomNumber()
    }

    fun generateRandomNumber() {
        val randomNumber = (1..10).random()
        _numberToGuess.value = randomNumber
    }

    fun decrementAttempts() {
        _attemptsRemaining.value = (_attemptsRemaining.value ?: 0) - 1
    }
}
