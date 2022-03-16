package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.loopj.android.http.AsyncHttpClient

class MainActivity : AppCompatActivity() {

    private var listData: ArrayList<UserData> = ArrayList()
    private lateinit var adapter: UserAdapter

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github Search"

        adapter = UserAdapter(listData)

        recyclerViewConfig()
        searchUser()
        getUser()
    }

    private fun searchUser(){ search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            if (query.isEmpty()){
                return true
            } else {
                listData.clear()
                getUserSearch(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
    }

    private fun getUser(){
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("")
    }
}