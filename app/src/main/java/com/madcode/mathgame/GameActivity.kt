package com.madcode.mathgame


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var rootLayout: ConstraintLayout
    lateinit var score: TextView
    lateinit var life: TextView
    lateinit var time: TextView
    lateinit var question: TextView
    lateinit var answer: EditText
    lateinit var okBtn: Button
    lateinit var nextBtn: Button

    lateinit var timer: CountDownTimer
    val max_time: Long = 60000
    var current_time: Long = max_time

    var system_answer = 0
    var user_answer = 0

    val target_score = 10 // Number of questions answered correctly before the game ends
    val system_life = 3

    var user_score = 0
    var user_life = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        rootLayout = findViewById(R.id.constraintLayout)
        score = findViewById(R.id.textViewScore)
        life = findViewById(R.id.textViewLife)
        time = findViewById(R.id.textViewTime)
        question = findViewById(R.id.textViewQns)
        answer = findViewById(R.id.editTextAnswer)
        okBtn = findViewById(R.id.buttonOK)
        nextBtn = findViewById(R.id.buttonNEXT)

        score.text = user_score.toString()
        life.text = system_life.toString()
        time.text = (max_time / 1000).toInt().toString()

        val opCode = intent.getStringExtra("opcode").toString()
        when (opCode) {
            "+" -> supportActionBar!!.title = "Addition"
            "-" -> supportActionBar!!.title = "Subtraction"
            "*" -> supportActionBar!!.title = "Multiplication"
        }

        gameContinue(opCode)

        okBtn.setOnClickListener {

            if (question.text.toString() == "Correct answer!" ||
                question.text.toString() == "Wrong answer!")
            {
                Snackbar.make(rootLayout, "Question already answered. Press NEXT for the next question...", Snackbar.LENGTH_LONG)
                    .setAction("Close", View.OnClickListener {
                    }).show()
            }
            else if (question.text.toString() == "Sorry, time is up!")
            {
                Snackbar.make(rootLayout, "Times up. Press NEXT for the next question...", Snackbar.LENGTH_LONG)
                    .setAction("Close", View.OnClickListener {
                    }).show()
            }
            else if (answer.text.toString() == "")
            {
                Toast.makeText(this@GameActivity, "Please enter an answer and press OK or skip to the next question by clicking NEXT", Toast.LENGTH_LONG).show()
            }
            else
            {
                pauseTimer()

                user_answer = answer.text.toString().toInt()
                if (user_answer == system_answer)
                {
                    question.text = "Correct answer!"
                    user_score++
                    score.text = user_score.toString()
                }
                else
                {
                    question.text = "Wrong answer!"
                    user_life--
                    life.text = user_life.toString()
                }
            }
        }

        nextBtn.setOnClickListener {
            pauseTimer()
            resetTimer()

            answer.setText("")

            if (user_life == 0)
            {
                Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score", user_score)
                startActivity(intent)
                finish()
            }
            else
            {
                gameContinue(opCode)
            }
        }

    }

    fun gameContinue(opCode: String)
    {
        val random = Random(System.currentTimeMillis())
        val num1 = random.nextInt(0, 100)
        val num2 = random.nextInt(0, 100)

        val equation: String
        system_answer = when(opCode)
        {
            "+" -> {
                equation = "$num1 + $num2"
                num1 + num2
            }
            "-" -> {
                equation = "$num1 - $num2"
                num1 - num2
            }
            "*" -> {
                equation = "$num1 * $num2"
                num1 * num2
            }
            else -> {
                equation = "Error"
                0
            }
        }

        question.text = equation

        startTimer()
    }

    fun startTimer()
    {
        timer = object : CountDownTimer(current_time, 1000) {
            override fun onTick(timeUntilEnd: Long) {
                current_time = timeUntilEnd
                updateTime()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()

                user_life--
                life.text = user_life.toString()
                question.text = "Sorry, time is up!"
            }
        }.start()
    }

    fun updateTime()
    {
        val remainingTime: Int = (current_time / 1000).toInt()
        time.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    fun pauseTimer()
    {
        timer.cancel()
    }

    fun resetTimer()
    {
        current_time = max_time
        updateTime()
    }

}