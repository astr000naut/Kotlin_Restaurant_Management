package com.example.sample.model.apirequest

import com.example.sample.model.BP_Dish

data class UpdateUser(
    val id: Int,
    val ten: String,
    val tuoi: Int,
    val sdt: String,
    val noio: String,
    val email: String,
    var oldpass: String = "",
    var newpass: String = ""
)