package com.example.licious.model.api

data class Data(
    val filters: List<Filter>,
    val info_badge: String,
    val info_message: String,
    val products: List<Product>,
    val title: String
)