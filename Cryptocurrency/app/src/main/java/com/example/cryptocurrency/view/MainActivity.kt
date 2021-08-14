package com.example.cryptocurrency.view

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.CryptoAdapter
import com.example.cryptocurrency.model.CryptoData
import com.example.cryptocurrency.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:MainViewModel
    private lateinit var recyclerViewAdapter:CryptoAdapter
    private var modelList:ArrayList<CryptoData> = ArrayList()
    private var displayList:ArrayList<CryptoData> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager

        startFunc()

    }




    private fun observeLiveData(){

        viewModel.currency.observe(this, {
             if(it.isEmpty()){
                 modelList = it
                 recyclerViewAdapter.updateList(ArrayList())

             }
            else{
                 modelList = it

                 recyclerViewAdapter.updateList(modelList)

            }
         })
        viewModel.progressBarStatus.observe(this, {
            it?.let {
                if (it){
                    progressBar.visibility = View.VISIBLE

                }
                else{
                    progressBar.visibility = View.GONE

                }
            }
        })


    }

    override fun onResume() {
        super.onResume()
        startFunc()


    }


    private fun startFunc(){
        if (isNetworkAvailable()){
            viewModel.loadData()
            observeLiveData()
            recyclerViewAdapter = CryptoAdapter(modelList = modelList )
            recyclerview.adapter = recyclerViewAdapter
            errorview.visibility = View.GONE
        }
        else{
            recyclerViewAdapter = CryptoAdapter(modelList = modelList )
            recyclerview.adapter = recyclerViewAdapter
            errorview.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            Toast.makeText(this,"Connection Failed",Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun refreshButton(){
        startFunc()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu!!.findItem(R.id.action_search)


        if (menuItem != null){
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            displayList.clear()
                            val search = newText.lowercase(Locale.getDefault())
                            modelList.forEach {
                                if (it.currency.lowercase(Locale.getDefault()).contains(search)) {
                                    displayList.add(it)
                                    recyclerViewAdapter.updateList(displayList)
                                }
                            }
                        }
                        else{
                            recyclerViewAdapter.updateList(modelList)
                        }
                    }

                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh){
            refreshButton()
        }


        return super.onOptionsItemSelected(item)

    }










}
