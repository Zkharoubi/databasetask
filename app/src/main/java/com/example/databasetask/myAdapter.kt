package com.example.databasetask



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView




class MyDataAdapter(private val review: List<database>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {

    // ViewHolder class to hold the views for each item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val review: TextView = itemView.findViewById(R.id.review)
        //  val imageView: ImageView = itemView.findViewById(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = review[position]
        holder.username.text = item.Username
        holder.review.text = item.review
        // Log.d("tag9", "Image URL: ${item.imageUrl}")
        /* Glide.with(holder.itemView.context)
            .load(item.imageUrl)
             .placeholder(R.drawable.ic_launcher_foreground)
             .error(R.drawable.ic_launcher_background)
            .into(holder.imageView)*/



    }

    override fun getItemCount(): Int {
        return review.size
    }
}

