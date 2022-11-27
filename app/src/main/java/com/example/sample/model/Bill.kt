package com.example.sample.model

data class Bill(
    val id: Int,
    val taoboi: String,
    val ban: Int,
    val thanhtoanboi: Int?,
    val createdAt: String,
    val updatedAt: String,
    val gia: Int?

) {}