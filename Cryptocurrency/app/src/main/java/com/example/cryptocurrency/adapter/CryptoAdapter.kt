package com.example.cryptocurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.GridlistBinding
import com.example.cryptocurrency.model.CryptoData

class CryptoAdapter( val modelList:ArrayList<CryptoData>):RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {
    class ViewHolder(var binding:GridlistBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<GridlistBinding>(inflater, R.layout.gridlist,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.cryptodata = modelList[position]
    }

    override fun getItemCount(): Int {
       return modelList.size
    }

    fun updateList(newList: ArrayList<CryptoData>){
        modelList.clear()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }


}