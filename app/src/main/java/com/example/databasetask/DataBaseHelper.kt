package com.example.databasetask

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

object DataBaseHelper { private const val JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"
    private const val DB_URL = "jdbc:mysql://10.0.2.2:3306/review"
    private const val USER = "root"
    private const val PASS = ""

    init {
        try {
            Class.forName(JDBC_DRIVER)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(DB_URL, USER, PASS)
                .also{ Log.d("tag1", "data found in query")}
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("tag2", "error in connection")
            null
        }
    }

    fun executeQuery(query: String): ResultSet? {
        Log.d("tag3", "excuting query")
        return try {

            val conn = getConnection()
            val stmt: Statement? = conn?.createStatement()
            stmt?.executeQuery(query).also { Log.d("tag4", "excuting query") }
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("tag6", "excuting query")
            null
        }
    }
}
