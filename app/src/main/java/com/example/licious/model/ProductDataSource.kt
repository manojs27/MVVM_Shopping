package com.example.licious.model

import com.example.licious.model.data.ProductsDetailsModel
import com.example.licious.network.OperationCallback

interface ProductDataSource {
    fun retrieveMaster(callback: OperationCallback<ProductsDetailsModel>)
    fun cancel()
}