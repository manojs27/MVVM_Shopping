package com.example.licious.model.api

data class ProductPricing(
    val base_price: Double,
    val cgst: Double,
    val city_id: Int,
    val created_at: Any,
    val hub_id: Int,
    val price_gram: Double,
    val product_id: String,
    val sgst: Double,
    val unit_gram: Int,
    val updated_at: Any
)