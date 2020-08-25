package com.example.licious.model.api

data class ProductInventory(
    val cat_id: Int,
    val created_at: Any,
    val dispatched_quantity: Int,
    val hub_id: Int,
    val merchant_delta: Int,
    val product_id: String,
    val rm_buffer: Int,
    val stock_lock: Int,
    val stock_units: Int,
    val updated_at: Any
)