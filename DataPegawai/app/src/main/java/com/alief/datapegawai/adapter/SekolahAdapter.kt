package com.alief_datapegawai.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alief.datapegawai.R
import com.alief_datapegawai.DetailSekolahActivity
import com.alief_datapegawai.api.ApiClient
import com.alief_datapegawai.model.SekolahResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SekolahAdapter(

    val dataSekolah: ArrayList<SekolahResponse.ListItems>,
//    private val onLongClick: (SekolahResponse.ListItems) -> Unit
): RecyclerView.Adapter<SekolahAdapter.ViewHolder>()
{
    class  ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgSekolah = view.findViewById<ImageView>(R.id.imgSekolah)
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvNoTelp = view.findViewById<TextView>(R.id.tvNoTelp)
        val tvAkreditasi = view.findViewById<TextView>(R.id.tvAkreditasi)
        
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.layout_item_sekolah,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return dataSekolah.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        val hasilResponse = dataSekolah[position]
        Picasso.get().load(hasilResponse.gambar).into(holder.imgSekolah)
        holder.tvNama.text = hasilResponse.nama
        holder.tvNoTelp.text = hasilResponse.no_telp
        holder.tvAkreditasi.text = hasilResponse.akreditasi

        holder.itemView.setOnClickListener(){
            val intent = Intent(holder.itemView.context,DetailSekolahActivity::class.java).apply {
                putExtra("gambar", hasilResponse.gambar)
                putExtra("nama",hasilResponse.nama)
                putExtra("no_telp", hasilResponse.no_telp)
                putExtra("akreditasi", hasilResponse.akreditasi)
                putExtra("informasi_sekolah", hasilResponse.informasi_sekolah)
            }
            holder.imgSekolah.context.startActivity(intent)
        }
//        holder.itemView.setOnLongClickListener {
//            onLongClick(hasilResponse) // Panggil listener long click
//            true
//        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")
                setIcon(R.drawable.ic_delete)

                setPositiveButton("Yakin") { dialogInterface, i ->
                    ApiClient.apiService.delSekolah(hasilResponse.id)
                        .enqueue(object : Callback<SekolahResponse> {
                            override fun onResponse(
                                call: Call<SekolahResponse>,
                                response: Response<SekolahResponse>
                            ) {
                                if (response.body()!!.success) {
                                    removeItem(position)
                                } else {
                                    Toast.makeText(
                                        holder.itemView.context,
                                        response.body()!!.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                            override fun onFailure(call: Call<SekolahResponse>, t: Throwable) {
                                Toast.makeText(
                                    holder.itemView.context,
                                    "Ada Kesalahan Server",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        })
                    dialogInterface.dismiss()
                }

                setNegativeButton("Batal") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }.show()

            true
        }

    }
    fun removeItem(position: Int) {
        dataSekolah.removeAt(position)
        notifyItemRemoved(position) // Notify the position of the removed item
        notifyItemRangeChanged(position, dataSekolah.size - position) // Optional: Adjust for index shifts
    }

    fun setData(data: List<SekolahResponse.ListItems>){
        dataSekolah.clear()
        dataSekolah.addAll(data)
//        notifyDataSetChanged()
    }

}