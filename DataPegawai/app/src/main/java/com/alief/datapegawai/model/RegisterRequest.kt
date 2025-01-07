package com.alief_datapegawai.model

data class RegisterRequest(
    val username : String,
    val password : String,
    val fullname : String,
    val email : String
)
