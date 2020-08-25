package com.example.licious.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.licious.R
import com.example.licious.model.data.ProductsDetailsModel
import com.squareup.picasso.Picasso
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.text.SpannableStringBuilder


class ProductViewAdapter(
    private val context: Context,
    private var dataLists: List<ProductsDetailsModel>
) : BaseAdapter() {

    override fun getCount(): Int {
        return dataLists.size
    }

    override fun getItem(pos: Int): Any {
        return dataLists[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_data, viewGroup, false)
        }

        val nameTxt: TextView = view!!.findViewById(R.id.NameTextView)
        val recTxt: TextView = view!!.findViewById(R.id.RecTextView)
        val weightTxt: TextView = view.findViewById(R.id.WeightTextView)
        val priceTxt: TextView = view.findViewById(R.id.PriceTextView)
        val prodImageView: ImageView = view.findViewById(R.id.cropsImage)
        val thisProducts = dataLists[position]

        nameTxt.setText(thisProducts.prod_name)
        weightTxt.setText("Net Wt. " + thisProducts.prod_weight.toString())
        var price: String = getPrice(thisProducts.prod_baseprice, thisProducts.prod_sellprice)
        val ssb = SpannableStringBuilder(price)
        ssb.append("  ");
        val strikethroughSpan = StrikethroughSpan()
        ssb.append(" \u20B9" + thisProducts.prod_baseprice.toString());
        ssb.setSpan(
            strikethroughSpan,
            ssb.length - thisProducts.prod_baseprice.toString().length,
            ssb.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        priceTxt.setText(ssb, TextView.BufferType.EDITABLE)
        recTxt.visibility = isRecommdedProduct(thisProducts.recommended!!)
        if (thisProducts != null) {
            Picasso.get().load(thisProducts.ImgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(prodImageView)
        } else {
            Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show()
            Picasso.get().load(R.drawable.ic_launcher_background).into(prodImageView)
        }
        return view
    }

    private fun isRecommdedProduct(value: Boolean): Int {
        if (value) return View.VISIBLE
        return View.GONE
    }

    private fun getPrice(value1: Int, value2: Int): String {
        var selling: Int = value2
        if (value2 > value1) {
            selling = value1
        }
        return "\u20B9" + selling
    }

    fun update(data: List<ProductsDetailsModel>) {
        dataLists = data
        notifyDataSetChanged()
    }
}