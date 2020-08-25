package com.example.licious.di

import androidx.lifecycle.ViewModelProvider
import com.example.licious.model.ProductDataSource
import com.example.licious.model.ProductRepository
import com.example.licious.viewmodel.ViewModelFactory

object Injection {

    private val productDataSource: ProductDataSource = ProductRepository()
    private val productViewModelFactory = ViewModelFactory(productDataSource)

    fun providerRepository(): ProductDataSource {
        return productDataSource
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return productViewModelFactory
    }
}