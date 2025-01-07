package com.alief_datapegawai

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alief.datapegawai.R
import com.squareup.picasso.Picasso

class DetailSekolahActivity : AppCompatActivity() {
    private lateinit var imgSekolah : ImageView
    private lateinit var tvNama : TextView
    private lateinit var tvNoTelp : TextView
    private lateinit var tvAkreditasi : TextView
    private lateinit var tvInformasi : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_sekolah)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgSekolah = findViewById(R.id.imgSekolah)
        tvNama = findViewById(R.id.tvNama)
        tvNoTelp = findViewById(R.id.tvNoTelp)
        tvAkreditasi = findViewById(R.id.tvAkreditasi)
        tvInformasi = findViewById(R.id.tvInformasi)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgSekolah)
        tvNama.text = intent.getStringExtra("nama")
        tvNoTelp.text = intent.getStringExtra("no_telp")
        tvAkreditasi.text = intent.getStringExtra("akreditasi")
        tvInformasi.text = intent.getStringExtra("informasi_sekolah")
    }
}