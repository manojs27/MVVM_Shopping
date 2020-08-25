package com.example.licious.model

import android.util.Log
import com.example.licious.model.api.MasterData
import com.example.licious.model.data.ProductsDetailsModel
import com.example.licious.network.ApiClient
import com.example.licious.network.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "CONSOLE"

class ProductRepository : ProductDataSource {
    override fun retrieveMaster(callback: OperationCallback<ProductsDetailsModel>) {
        call= ApiClient.build()?.products()
        call?.enqueue(object :Callback<MasterData>{
            override fun onFailure(call: Call<MasterData>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<MasterData>, response: Response<MasterData>) {
                response.body()?.let {
                    if(response.isSuccessful && (it.isSuccess())){
                        Log.v(TAG, "data ${it.data}")
                       callback.onSuccess(loadContents(it))
                    }else{
                        callback.onError(it.statusMessage)
                    }
                }
            }
        })
    }

    private var call: Call<MasterData>? = null

    private fun loadContents(masterData: MasterData) : List<ProductsDetailsModel> {
        var ProductList: MutableList<ProductsDetailsModel> = mutableListOf<ProductsDetailsModel>()
        for (products in masterData.data.products) {
            var productsDetailsModel = ProductsDetailsModel()
            productsDetailsModel.prod_name = products.product_master.pr_name
            productsDetailsModel.recommended =products.product_merchantdising.recommended
            productsDetailsModel.ImgUrl = products.product_merchantdising.pr_image
            productsDetailsModel.prod_baseprice = products.product_pricing.base_price.toInt()
            productsDetailsModel.prod_sellprice = (products.product_pricing.unit_gram * products.product_pricing.price_gram).toInt()
            productsDetailsModel.prod_weight = products.product_master.pr_weight.toString()
            ProductList!!.add(productsDetailsModel)
        }
        return ProductList
    }
    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}