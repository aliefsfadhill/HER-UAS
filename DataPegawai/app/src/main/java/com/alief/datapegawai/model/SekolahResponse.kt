package com.alief_datapegawai.model

data class SekolahResponse(
    val success : Boolean,
    val message : String,
    val data : ArrayList<ListItems>
) {
    data class ListItems(
        val id: String,
        val nama: String,
        val informasi_sekolah: String,
        val no_telp : String,
        val akreditasi : String,
        val gambar : String,

    )
}

