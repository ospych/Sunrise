package com.example.sunrise

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunrise.adapter.Adapter
import com.example.sunrise.repository.Repository
import com.example.sunrise.retrofit.Api
import com.example.sunrise.retrofit.RetrofitBuilder
import com.example.sunrise.viewModel.MainViewModel
import com.example.sunrise.viewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    val adapter by lazy { Adapter() }

    lateinit var mainViewModel: MainViewModel
    private lateinit var service: Api

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = RetrofitBuilder.getInstance()

        if (isNetworkAvailable(this) || isInternetAvailable()) {
            getItems()
            refresh.setOnClickListener {
                getItems()
            }
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }


    }

    private fun getItems() {
        progressBar.visibility = View.VISIBLE
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getItems()
        mainViewModel.items.observe(this, { response ->
            if (response.isSuccessful) {
                response.body().let {
                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkAvailable(context: Context): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: java.lang.Exception) {
            false
        }
    }
}