package com.cartrack.omdapi.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cartrack.omdapi.data.entities.MediaContent
import com.cartrack.omdapi.databinding.FragmentMediaItemBinding
import com.cartrack.omdapi.ui.placeholder.PlaceholderContent.PlaceholderItem

class MediaListAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MediaListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMediaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide.with(holder.imageView.context)
            .load("url")
            .placeholder(ColorDrawable(Color.GRAY))
            .circleCrop()
            .into(holder.imageView)
        holder.mediaTitle.text = item.content
        holder.mediaDescription.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.mediaPosterImage
        val mediaTitle: TextView = binding.mediaTitle
        val mediaDescription: TextView = binding.mediaDescription
    }

    fun setData(items: List<MediaContent>?) {

    }

}