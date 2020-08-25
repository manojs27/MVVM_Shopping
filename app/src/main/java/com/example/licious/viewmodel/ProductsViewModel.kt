package com.example.licious.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.licious.model.ProductDataSource
import com.example.licious.model.data.ProductsDetailsModel
import com.example.licious.network.OperationCallback

class ProductsViewModel(private val repository: ProductDataSource):ViewModel() {

    private val _prods = MutableLiveData<List<ProductsDetailsModel>>().apply { value = emptyList() }
    val prods: LiveData<List<ProductsDetailsModel>> = _prods

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    fun loadMuseums(){
        _isViewLoading.postValue(true)
        repository.retrieveMaster(object: OperationCallback<ProductsDetailsModel> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( error)
            }

            override fun onSuccess(data: List<ProductsDetailsModel>?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _prods.value= data
                    }
                }
            }
        })
    }

}