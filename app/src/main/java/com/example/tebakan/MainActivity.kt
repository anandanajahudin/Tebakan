package com.example.tebakan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: NumberViewModel by viewModels()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameButton.isEnabled = false

        viewModel.numberToGuess.observe(this, Observer { numberToGuess ->
            numberToGuessTextView.text = getString(R.string.number_to_guess, numberToGuess)
        })

        viewModel.attemptsRemaining.observe(this, Observer { attemptsRemaining ->
            attemptsRemainingTextView.text = getString(R.string.attempts_remaining, attemptsRemaining)
            if (attemptsRemaining == 0) {
                gameOver()
                newGameButton.isEnabled = true
            } else {
                newGameButton.isEnabled = false
            }
        })

        guessButton.setOnClickListener {
            val guessedNumber = guessEditText.text.toString().toIntOrNull()

            if (guessedNumber != null) {
                val numberToGuess = viewModel.numberToGuess.value

                if (guessedNumber == numberToGuess) {
                    score++
                    scoreTextView.text = getString(R.string.score, score)
                    viewModel.generateRandomNumber()
                } else {
                    viewModel.decrementAttempts()
                }
            }
        }

        newGameButton.setOnClickListener {
            viewModel.generateRandomNumber()
            viewModel.attemptsRemaining
            guessEditText.text.clear()
            score = 0
            scoreTextView.text = getString(R.string.score, score)
            resultTextView.text = ""
            attemptsRemainingTextView.text = getString(R.string.attempts_remaining, 3)
            guessButton.isEnabled = true
        }
    }

    private fun gameOver() {
        resultTextView.text = getString(R.string.game_over)
        guessButton.isEnabled = false
        scoreTextView.text = getString(R.string.score, score)
    }
}