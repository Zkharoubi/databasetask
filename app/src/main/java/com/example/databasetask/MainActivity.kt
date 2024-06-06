package com.example.databasetask

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val userList = mutableListOf<database>()
    private lateinit var adapter: MyDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyDataAdapter(userList)
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            fetchdatabase()
        }


    }

    private suspend fun fetchdatabase() {
        val query = "SELECT id,  Username, review FROM reviews"
        var connection: Connection? = null
        var resultSet: ResultSet? = null

        try {
            connection = DataBaseHelper.getConnection()
            if (connection != null) {
                Log.d("tag0", "data found in query")
                val statement = connection.createStatement()
                resultSet = statement.executeQuery(query)

                if (resultSet != null) {
                    val newList = mutableListOf<database>()
                    while (resultSet.next()) {
                        val id = resultSet.getInt("id")
                        // val imageUrl = resultSet.getString("imageUrl")
                        val Username = resultSet.getString("Username")
                        val review = resultSet.getString("review")

                        val reviews = database(id, Username, review)
                        newList.add(reviews)
                    }
                    withContext(Dispatchers.Main) {
                        userList.clear()
                        userList.addAll(newList)
                        updateUI()
                    }
                }else{
                    Log.d("tag6", "No data found in query")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "error in connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("tag1", "error in query ", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity,"error in query", Toast.LENGTH_SHORT).show()
            }
        }
        finally{
            try {
                resultSet?.close()
                connection?.close()
            }catch (e: SQLException){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI() {
        adapter.notifyDataSetChanged()
    }



}
