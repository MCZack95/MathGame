package com.madcode.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var addBtn: Button
    lateinit var subBtn: Button
    lateinit var mulBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn = findViewById(R.id.buttonAdd)
        subBtn = findViewById(R.id.buttonSub)
        mulBtn = findViewById(R.id.buttonMul)

        addBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("opcode", "+")
            startActivity(intent)
        }

        subBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("opcode", "-")
            startActivity(intent)
        }

        mulBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("opcode", "*")
            startActivity(intent)
        }

    }
}