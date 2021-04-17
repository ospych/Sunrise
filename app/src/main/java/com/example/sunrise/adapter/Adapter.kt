package com.example.sunrise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrise.R
import com.example.sunrise.retrofit.data.JokeItem
import kotlinx.android.synthetic.main.item.view.*

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<JokeItem>() {
        override fun areItemsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent, false))
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemView.apply {
            holder.itemView.setup.text = item.setup
            holder.itemView.punchline.text = item.punchline
            holder.itemView.idItem.text = item.id.toString()
            holder.itemView.type.text = item.type
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}