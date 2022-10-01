package com.znotagain.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    lateinit var result: TextView
    lateinit var btnAgain: Button
    lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        result = findViewById(R.id.textViewResult)
        btnAgain = findViewById(R.id.buttonAgain)
        btnExit = findViewById(R.id.buttonExit)

        val finalScore = intent.getIntExtra("score", 0)
        result.text = "Score: ${finalScore}"

        btnAgain.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnExit.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}