package com.example.picabee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picabee.Hit
import com.example.picabee.R

object HomeDiffCallback : DiffUtil.ItemCallback<Hit>() {
    override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
        return oldItem == newItem
    }
}

class HomeAdapter : ListAdapter<Hit, HomeAdapter.HomeViewHolder>(HomeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.home_item_view, parent, false)
            .let { HomeViewHolder(it) }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position).apply {
            holder.likesTv.text = likes.toString()
            holder.favoritesTv.text = favorites.toString()
            holder.imageView.layoutParams.height = webformatHeight
            Glide.with(holder.likesTv.context)
                .load(webformatURL)
                .placeholder(R.color.colorAccent)
                .centerCrop()
                .into(holder.imageView);
        }
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val likesTv: TextView = itemView.findViewById(R.id.likesTv)
        val favoritesTv: TextView = itemView.findViewById(R.id.favoritesTv)
    }
}