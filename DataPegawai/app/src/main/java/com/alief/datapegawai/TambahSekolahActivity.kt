package com.alief_datapegawai

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alief.datapegawai.R
import com.github.dhaval2404.imagepicker.ImagePicker

import com.alief_datapegawai.api.ApiClient
import com.alief_datapegawai.model.TambahSekolahResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TambahSekolahActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etNoTelp: EditText
    private lateinit var etNip: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etInformasi: EditText
    private lateinit var btnGambar: Button
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_sekolah)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etNama = findViewById(R.id.etNama)
        btnTambah = findViewById(R.id.btnTambah)
        etInformasi = findViewById(R.id.etInformasi)
        etNip = findViewById(R.id.etNip)
        etNoTelp = findViewById(R.id.etNoTelp)
        etAlamat = findViewById(R.id.etAkreditasi)

        progressBar = findViewById(R.id.progressBar)


        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                tambahSekolah(etNama.text.toString(), etNoTelp.text.toString(), etAlamat.text.toString(), etInformasi.text.toString(), file)
            }
        }

    }

    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    //proses tambah berita
    private fun tambahSekolah(nama: String, no_telp: String, akreditasi : String, informasi_sekolah : String, gambar : File) {
        progressBar.visibility = View.VISIBLE
        val requestBody = gambar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partFileGambar =
            MultipartBody.Part.createFormData("gambar", gambar.name, requestBody)

        val title = nama.toRequestBody("text/plain".toMediaTypeOrNull())
        val telepon = no_telp.toRequestBody("text/plain".toMediaTypeOrNull())
        val akre = akreditasi.toRequestBody("text/plain".toMediaTypeOrNull())
        val deskripsiSekolah = informasi_sekolah.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addSekolah(title,telepon,akre, deskripsiSekolah, partFileGambar)
            .enqueue(object : Callback<TambahSekolahResponse> {
                override fun onResponse(
                    call: Call<TambahSekolahResponse>,
                    response: Response<TambahSekolahResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            startActivity(
                                Intent(
                                    this@TambahSekolahActivity,
                                    DashboardActivity::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@TambahSekolahActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@TambahSekolahActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<TambahSekolahResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahSekolahActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            })
    }
}