package com.example.licious.model.api

data class Product(
    val product_inventory: ProductInventory,
    val product_master: ProductMaster,
    val product_merchantdising: ProductMerchantdising,
    val product_pricing: ProductPricing
)