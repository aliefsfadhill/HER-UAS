package com.alief_datapegawai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alief.datapegawai.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(){
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //kehalaman register
        btnRegister.setOnClickListener(){
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}