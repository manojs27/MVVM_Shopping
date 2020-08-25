package com.example.licious.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.licious.R
import com.example.licious.di.Injection
import com.example.licious.model.data.ProductsDetailsModel
import com.example.licious.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.main_activity.*

class ProductsActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductsViewModel
    private var adapter: ProductViewAdapter? = null

    companion object {
        const val TAG= "CONSOLE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI(){
        adapter = ProductViewAdapter(
            this,
            viewModel.prods.value ?: emptyList()
        )
        llGridView.adapter = adapter
    }

    //viewmodel
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(ProductsViewModel::class.java)
        viewModel.prods.observe(this,renderMuseums)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderMuseums= Observer<List<ProductsDetailsModel>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility=View.GONE
        layoutEmpty.visibility=View.GONE
        adapter!!.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility=if(it)View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility=View.VISIBLE
        layoutEmpty.visibility=View.GONE
        textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility=View.VISIBLE
        layoutError.visibility=View.GONE
    }

     override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
     }

}
